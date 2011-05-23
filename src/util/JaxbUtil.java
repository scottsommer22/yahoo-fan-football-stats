package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

/**
 * 
 * A class to aid in marshalling and unmarshalling Jaxb classes.
 * 
 */
public final class JaxbUtil {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(JaxbUtil.class);

    /**
     * Cannot create a JaxbUtil.
     */
    private JaxbUtil() {
    }

    /**
     * Write the object to a file.
     * 
     * @param <T>
     *            the type of object to write
     * @param filePath
     *            the path to write to
     * @param jaxbObject
     *            the object. Must be jaxb annotated.
     */
    public static <T> void writeToFile(String filePath, T jaxbObject) {
        Writer output = null;

        try {

            output = new BufferedWriter(new FileWriter(filePath));

            JAXBContext ctx = JAXBContext.newInstance(jaxbObject.getClass());

            Marshaller marshaller = ctx.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(jaxbObject, output);

        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                LOGGER.debug("Could not close");
            }
        }

    }

    /**
     * Write an object to a stream.
     * 
     * @param <T>
     *            the type of object
     * @param stream
     *            the stream
     * @param object
     *            the object
     */
    public static <T> void marshall(OutputStream stream, T object) {
        try {

            JAXBContext ctx = JAXBContext.newInstance(object.getClass());

            Marshaller marshaller = ctx.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(object, stream);

        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Unmarshall from stream.
     * 
     * @param <T>
     *            the type to unmarshall
     * @param stream
     *            the stream
     * @param objectClass
     *            the object class
     * @return the object
     */
    public static <T> T unmarshall(InputStream stream, Class<T> objectClass) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(objectClass);

            Unmarshaller unmarshaller = ctx.createUnmarshaller();

            T object = objectClass.cast(unmarshaller.unmarshal(stream));

            return object;

        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Load an object from a file.
     * 
     * @param <T>
     *            The type to return
     * @param filePath
     *            the path of the file
     * @param objectClass
     *            the class type to return
     * @return the object. Null if the file does not exist
     */
    public static <T> T loadFromFile(String filePath, Class<T> objectClass) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(objectClass);

            Unmarshaller unmarshaller = ctx.createUnmarshaller();

            File file = new File(filePath);

            if (!file.exists()) {
                LOGGER.debug("Could not find file " + filePath);
                return null;
            }

            T object = objectClass.cast(unmarshaller.unmarshal(file));

            return object;

        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
