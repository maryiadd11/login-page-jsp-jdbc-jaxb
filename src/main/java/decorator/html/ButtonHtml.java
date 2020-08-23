package decorator.html;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ButtonHtml {

    public final static String BUTTON_ON_CLICK_ACTION = "window.location.href='%s';";

    @XmlValue
    private String button;

    @XmlAttribute
    private String onclick;

    @XmlAttribute
    private String type;
}
