package jp.sf.amateras.cookiesession.encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import javax.servlet.FilterConfig;

import jp.sf.amateras.cookiesession.exception.EncoderException;
import jp.sf.amateras.cookiesession.exception.InitializationException;
import jp.sf.amateras.cookiesession.util.IOUtil;
import net.arnx.jsonic.util.Base64;

/**
 * An implementation of {@link SessionEncoder} which encodes and decodes session attributes as Base64 encoded serialized binary.
 * If you want to use this encoder, all session attribute values have to implement java.io.Serializable.
 *
 * @author Naoki Takezoe
 */
public class BinaryEncoder implements SessionEncoder {

	public void init(FilterConfig config) throws InitializationException {
	}

	public String encode(Map<String, Object> attributes) throws EncoderException {
		try {
			ByteArrayOutputStream bout = null;
			ObjectOutputStream oout = null;
			byte[] bytes = null;

			try {
				bout = new ByteArrayOutputStream();
				oout = new ObjectOutputStream(bout);
				oout.writeObject(attributes);

				bytes = bout.toByteArray();

			} finally {
				IOUtil.closeQuietly(bout);
				IOUtil.closeQuietly(oout);
			}

			String encoded = Base64.encode(bytes);

			return encoded;

		} catch(Exception ex){
			throw new EncoderException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> decode(String value) throws EncoderException {
		try {
			ByteArrayInputStream bin = null;
			ObjectInputStream oin = null;
			Map<String, Object> attributes = null;
			try {
				bin = new ByteArrayInputStream(Base64.decode(value));
				oin = new ObjectInputStream(bin);
				attributes = (Map<String, Object>) oin.readObject();

			} finally {
				IOUtil.closeQuietly(bin);
				IOUtil.closeQuietly(oin);
			}

			return attributes;

		} catch(Exception ex){
			throw new EncoderException(ex);
		}
	}

}
