package decorator.html;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlType(name = "", propOrder = {
        "type",
        "placeholder",
        "value",
        "name"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class InputHtml {

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String placeholder;

    @XmlAttribute
    private String value;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String required;
}
