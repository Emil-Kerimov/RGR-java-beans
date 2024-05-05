package xml;

import mybeans.DataSheet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class DataSheetSaveToXML {

    public static void marshalDataToXML(String fileName, DataSheet dataSheet) throws JAXBException {
        Marshaller marshaller = JAXBContext.newInstance(DataSheet.class).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(dataSheet, new File(fileName));

    }

    public static DataSheet unmarshalOutXMLs(String filePath) throws JAXBException {
        Unmarshaller unmarshaller = JAXBContext.newInstance(DataSheet.class).createUnmarshaller();
        Object element = unmarshaller.unmarshal(new File(filePath));
        if (element instanceof DataSheet) {
            return (DataSheet) element;
        } else throw new JAXBException("Перевірте файл");
    }
}