import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem Zhdanov <azhdanov@griddynamics.com>
 * @since 28/09/13
 */
public class Main {
    public static final int N = 1000000;
    public static final String FILE_NAME_PROTO = "users.ser";
    private static final String FILE_NAME_PROTO_FILTERED = "users_filtered.ser";

    public static void main(String[] args) throws Throwable {
       testProto();
       testXML();
    }

    private static void testProto() throws IOException {
        UserBookProto.UserBook.Builder data = UserBookProto.UserBook.newBuilder();
        FileOutputStream output = new FileOutputStream(FILE_NAME_PROTO);

        for (int i=0; i < N; i++) {
           UserBookProto.User user = UserBookProto.User.newBuilder().setId(i).setName(String.valueOf(i)).build();
           data.addPerson(user);
        }
        UserBookProto.UserBook book = data.build();

        long start = System.currentTimeMillis();
        book.writeTo(output);
        output.close();
        System.out.println("ProtoBuf serialization in " + (long)(System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream(FILE_NAME_PROTO);
        UserBookProto.UserBook bookFromFile =  UserBookProto.UserBook.parseFrom(fis);
        fis.close();
        System.out.println("ProtoBuf deserialization in " + (long)(System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        List<UserBookProto.User> usersList = bookFromFile.getPersonList().stream().parallel().filter(u -> u.getId() % 10 == 0).collect(Collectors.toList());
        System.out.println("Parallel filtering in " + (long)(System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        List<UserBookProto.User> usersParalelList = bookFromFile.getPersonList().stream().filter(u -> u.getId() % 10 == 0).collect(Collectors.toList());
        System.out.println("Non Parallel filtering in " + (long)(System.currentTimeMillis() - start));


        //System.out.println("Filtered proto collection size is " + usersList.size());
    }

    public static void testXML() throws Throwable{
        UserBookXML book = new UserBookXML();

        for (int i=0; i < N; i++) {
            UserXml user = new UserXml();
            user.setId(i);
            user.setName(String.valueOf(i));
            book.addUsers(user);
        }
        File file = new File("usersXml.ser");
        long start = System.currentTimeMillis();
        JAXBContext jaxbContext = JAXBContext.newInstance(UserBookXML.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.marshal(book, file);
        System.out.println("Xml serialization in " + (long)(System.currentTimeMillis() - start));

        FileOutputStream fos = new FileOutputStream("userJava.ser");
        start = System.currentTimeMillis();
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(book);
        fos.flush();
        System.out.println("Java serialization in " + (long)(System.currentTimeMillis() - start));
        fos.close();

    }
}
