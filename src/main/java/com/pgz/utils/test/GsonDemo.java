package com.pgz.utils.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Data;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * google的Gson的常用示例
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-12
 */
public class GsonDemo {

    @Test
    public void gsonTest() {
        //默认Gson，日期格式默认是字符串解析
        Gson gson0 = new Gson();

        String json0 = "{\"dateStr\":\"2020-02-13 00:00:00\",\"date\":1585670400000,\"string\":\"string\"}";
        String json1 = "{\"date\":\"2020-02-13 00:00:00\",\"dateStr\":\"2020-02-13 00:00:00\",\"string\":\"string\"}";
        Foo foo0 = gson0.fromJson(json1, Foo.class);
        System.out.println(foo0);

        //运用Json反序列化工具
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(
                        Date.class,
                        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(jsonElement.getAsJsonPrimitive()
                                .getAsLong()))
                .create();

        //运用Json反序列化工具未使用lambda表达式
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

            @Override
            public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        }).create();

        Foo foo = gson1.fromJson(json0, Foo.class);
        System.out.println(foo);

        //使用类型适配器反序列化
        Gson gson2 = new GsonBuilder().registerTypeAdapter(Foo.class, new FooTypeAdapter()).create();
        Foo foo2 = gson2.fromJson(json0, Foo.class);
        System.out.println(foo2);
    }

    /**
     * Gson使用之@Expose注解使用
     *
     * @author liquan_pgz@qq.com
     * date 2020-02-12
     **/
    @Test
    public void excludeAndExposeTest() {
        User user = new User();
        user.setEmailAddress("emailAddress");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        //如果你使用new Gson()实例化一个对象的话，那么@Expose 的注解是无效的，User中的参数都是会参与反序列化或序列化。
        Gson gson0 = new Gson();
        String json0 = gson0.toJson(user);
        System.out.println("基础Gson序列化操作===   " + json0);

        User fromJson0 = gson0.fromJson(json0, User.class);
        System.out.println("基础Gson反序列化操作===   " + fromJson0);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        String json1 = gson.toJson(user);
        System.out.println("排除不带Expose注解的Gson序列化操作===   " + json1);

        User fromJson1 = gson.fromJson(json0, User.class);
        System.out.println("排除不带Expose注解的Gson反序列化操作===   " + fromJson1);

        User fromJson2 = gson.fromJson(json1, User.class);
        System.out.println("排除不带Expose注解的Gson反序列化操作===   " + fromJson2);
    }

    @Test
    public void deserializeTest() {
        Gson gson = new Gson();
        String json1 = "{\"name\":\"名字\",\"img\":\"图片\"}";
        Book book1 = gson.fromJson(json1, Book.class);
        System.out.println("打印反序列化后的信息==== " + book1);

        //当有多个名称匹配的名称时会以最后一个覆盖上一个
        String json2 = "{\"title\":\"名字\",\"icon\":\"图片1\",\"image\":\"图片2\"}";
        Book book2 = gson.fromJson(json2, Book.class);
        System.out.println("打印反序列化后的信息==== " + book2);
    }

    @Data
    private static class Foo {

        private Date date;

        private Date dateStr;

        private String string;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public String toString() {
            return "Foo{" +
                    "date=" + sdf.format(date) +
                    ", dateStr=" + sdf.format(dateStr) +
                    ", string='" + string + '\'' +
                    '}';
        }
    }

    private static class FooTypeAdapter extends TypeAdapter<Foo> {

        //序列化操作
        @Override
        public void write(JsonWriter jsonWriter, Foo foo) throws IOException {
            jsonWriter.beginObject();//产生{
            jsonWriter.name("date").value(foo.getDate().getTime())
                    .name("string").value(foo.getString());
            jsonWriter.endObject();//产生}
        }

        //反序列化操作
        @Override
        public Foo read(JsonReader jsonReader) throws IOException {
            Foo foo = new Foo();
            jsonReader.beginObject();//解析{

            while (jsonReader.hasNext()) {
                switch (jsonReader.nextName()) {
                    case "date":
                        foo.setDate(new Date(jsonReader.nextLong()));
                        break;
                    case "string":
                        foo.setString(jsonReader.nextString());
                        break;
                }
            }

            jsonReader.endObject();//解析}
            return foo;
        }
    }

    //对Gson进行实例化，
    //1.那么 没有被@Expose注解的password将不会参与序列化及反序列化。
    //2.lastName不会参与序列化
    //3.emailAddress 不会参与序列化和反序列化
    @Data
    private static class User {

        //暴露出来的字段
        @Expose
        private String firstName;

        //暴露出来的字段，不进行序列化
        @Expose(serialize = false)
        private String lastName;

        //暴露出来的字段，不进行序列化和反序列化
        @Expose(serialize = false, deserialize = false)
        private String emailAddress;

        //未使用注解不参与序列化和反序列化
        private String password;

    }

    //可以看到这里我们在@SerializedName 注解使用了一个value, alternate字段,value也就是默认的字段，对序列化和反序列化都有效，
    // alternate只有反序列化才有效果。也就是说一般服务器返回给我们JSON数据的时候可能同样的一个图片，表示"image","img","icon"等，
    // 我们利用@SerializedName 中的alternate字段就能解决这个问题，全部转化为我们实体类中的图片字段。
    @Data
    private static class Book {

        @SerializedName("title")
        private String name;

        @SerializedName(value = "img", alternate = {"image", "icon"})
        private String img;
    }
}
