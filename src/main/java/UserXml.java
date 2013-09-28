import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Artem Zhdanov <azhdanov@griddynamics.com>
 * @since 28/09/13
 */
@XmlAccessorType(XmlAccessType.NONE)
public class UserXml implements Serializable{
    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
