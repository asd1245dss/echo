package io.summer.tool.weixin;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChangWei Li
 * @version 2018-03-21 10:05
 */
public class KryoTest {

    private static Kryo kryo = new Kryo();

    private static <T> void serializeToFile(T t, String path) throws FileNotFoundException {
        Output output = new Output(new FileOutputStream(path));

        kryo.writeObject(output, t);

        output.close();
    }

    private static <T> T deserializeFromFile(Class<T> tClass, String path) throws FileNotFoundException {
        Input input = new Input(new FileInputStream(path));

        Type type = new ParameterizedTypeReference<T>() {}.getType();

        T t = kryo.readObject(input, tClass);

        input.close();

        return t;
    }

    private static <T> String serilizeToJSON(T t) {
        Output output = new Output(new ByteArrayOutputStream());

        kryo.writeObject(output, t);

        output.flush();
        output.close();

        return new String(output.getBuffer());
    }

    private static <T> T deserilizeFromString(String json) {
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String path = "d:/file.bin";

        Map<String, String> resultMap = new HashMap<>(2);
        resultMap.put("a", "a");
        resultMap.put("b", "b");

        Message<Map<String, String>> message = new Message<>();
        message.setId("a1");
        message.setName("user1");
        message.setPass("pass2");
        message.setData(resultMap);

        serializeToFile(message, path);
        System.out.println(serilizeToJSON(message));
        message = deserializeFromFile(Message.class, path);
        System.out.println(message);
    }

}

@Data
class Message<T> {

    private String id;

    private String name;

    private String pass;

    private T data;

}
