package decorator.html;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlType(name = "", propOrder = {
        "td"
})
@XmlRootElement(name = "tr")
public class TrHtml {

    private List<TdHtml> td;
}
