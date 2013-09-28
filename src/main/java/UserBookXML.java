import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Artem Zhdanov <azhdanov@griddynamics.com>
 * @since 28/09/13
 */
@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.NONE)
public class UserBookXML implements Serializable{
    private List<UserXml> users = new LinkedList<UserXml>();

    @XmlElement(name = "users")
    public List<UserXml> getUsers() {
        return users;
    }

    public void addUsers(UserXml user) {
        this.users.add(user);
    }
}
