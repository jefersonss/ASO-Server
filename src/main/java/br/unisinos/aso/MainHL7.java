package br.unisinos.aso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.datatype.PN;
import ca.uhn.hl7v2.model.v22.message.ADT_A01;
import ca.uhn.hl7v2.model.v22.segment.MSH;
import ca.uhn.hl7v2.parser.*;

public class MainHL7 {

	public static void main(String[] args) {
		/*
		 * In an ideal world, systems would always send perfect messages, but in
		 * the real world, systems will always exist that send crazy data. HAPI
		 * tries to be tolerant of this, but occasionally a system will come
		 * along that sends something so wacky that HAPI is unable to parse it.
		 * 
		 * One possible solution to this is to manually fix such problems before
		 * parsing them. One easy way to do that is with a subclass of your
		 * parser.
		 */

		// This message has PI instead of PID in the second line:
		String msg = "MSH|^~\\&|HIS|RIH|EKG|EKG|199904140038||ADT^A01||P|2.2\r"
				+ "PI|0001|00009874|00001122|A00977|SMITH^JOHN^M|MOM|19581119|F|NOTREAL^LINDA^M|C|564 SPRING ST^^NEEDHAM^MA^02494^US|0002|(818)565-1551|(425)828-3344|E|S|C|0000444444|252-00-4414||||SA|||SA||||NONE|V1|0001|I|D.ER^50A^M110^01|ER|P00055|11B^M011^02|070615^BATMAN^GEORGE^L|555888^NOTREAL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^NOTREAL^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|199904101200||||5555112333|||666097^NOTREAL^MANNY^P\r"
				+ "NK1|0222555|NOTREAL^JAMES^R|FA|STREET^OTHER STREET^CITY^ST^55566|(222)111-3333|(888)999-0000|||||||ORGANIZATION\r"
				+ "PV1|0001|I|D.ER^1F^M950^01|ER|P000998|11B^M011^02|070615^BATMAN^GEORGE^L|555888^OKNEL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^VOICE^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|||||5555112333|||666097^DNOTREAL^MANNY^P\r"
				+ "PV2|||0112^TESTING|55555^PATIENT IS NORMAL|NONE|||19990225|19990226|1|1|TESTING|555888^NOTREAL^BOB^K^DR^MD||||||||||PROD^003^099|02|ER||NONE|19990225|19990223|19990316|NONE\r"
				+ "AL1||SEV|001^POLLEN\r"
				+ "GT1||0222PL|NOTREAL^BOB^B||STREET^OTHER STREET^CITY^ST^77787|(444)999-3333|(222)777-5555||||MO|111-33-5555||||NOTREAL GILL N|STREET^OTHER STREET^CITY^ST^99999|(111)222-3333\r"
				+ "IN1||022254P|4558PD|BLUE CROSS|STREET^OTHER STREET^CITY^ST^00990||(333)333-6666||221K|LENIX|||19980515|19990515|||PATIENT01 TEST D||||||||||||||||||02LL|022LP554";

		// Let's create a special parser to handle this:
		Parser parser = new PipeParser() {
			// We override the parse method to correct issues
			public Message parse(String theMessage) throws HL7Exception, EncodingNotSupportedException {
				theMessage = theMessage.replace("\rPI|", "\rPID|");
				return super.parse(theMessage);
			}
		};

		Message message = null;
		try {
			message = parser.parse(msg);
//			System.out.println(parser.encode(message));
//			System.out.println(message.getName());
		} catch (HL7Exception e) {
			e.printStackTrace();
		}

		 /* PI has been fixed:
        
        MSH|^~\&|HIS|RIH|EKG|EKG|199904140038||ADT^A01||P|2.2
        PID|0001|00009874|00001122|A00977|SMITH^JOHN^M|MOM|19581119|F|NOTREAL^LINDA^M|C|564 SPRING ST^^NEEDHAM^MA^02494^US|0002|(818)565-1551|(425)828-3344|E|S|C|0000444444|252-00-4414||||SA|||SA||||NONE|V1|0001|I|D.ER^50A^M110^01|ER|P00055|11B^M011^02|070615^BATMAN^GEORGE^L|555888^NOTREAL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^NOTREAL^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|199904101200||||5555112333|||666097^NOTREAL^MANNY^P
        NK1|0222555|NOTREAL^JAMES^R|FA|STREET^OTHER STREET^CITY^ST^55566|(222)111-3333|(888)999-0000|||||||ORGANIZATION
        PV1|0001|I|D.ER^1F^M950^01|ER|P000998|11B^M011^02|070615^BATMAN^GEORGE^L|555888^OKNEL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^VOICE^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|||||5555112333|||666097^DNOTREAL^MANNY^P
        PV2|||0112^TESTING|55555^PATIENT IS NORMAL|NONE|||19990225|19990226|1|1|TESTING|555888^NOTREAL^BOB^K^DR^MD||||||||||PROD^003^099|02|ER||NONE|19990225|19990223|19990316|NONE
        AL1||SEV|001^POLLEN
        GT1||0222PL|NOTREAL^BOB^B||STREET^OTHER STREET^CITY^ST^77787|(444)999-3333|(222)777-5555||||MO|111-33-5555||||NOTREAL GILL N|STREET^OTHER STREET^CITY^ST^99999|(111)222-3333
        IN1||022254P|4558PD|BLUE CROSS|STREET^OTHER STREET^CITY^ST^00990||(333)333-6666||221K|LENIX|||19980515|19990515|||PATIENT01 TEST D||||||||||||||||||02LL|022LP554
     */
		
		ADT_A01 adtMsg = (ADT_A01) message;
		MSH msh = adtMsg.getMSH();
		// Retrieve some data from the MSH segment
		String msgType = msh.getMessageType().getMessageType().getValue();
		String msgTrigger = msh.getMessageType().getTriggerEvent().getValue();

		// Prints "ADT A01"
//		System.out.println(msgType + " " + msgTrigger);

		/*
		 * Now let's retrieve the patient's name from the parsed message.
		 * 
		 * PN is an HL7 data type consisting of several components, such as
		 * family name, given name, etc.
		 */
		PN patientName = adtMsg.getPID().getPatientName();


		// Prints "SMITH"
		System.out.println(patientName.getFamilyName().getValue());
		System.out.println(patientName.getGivenName().getValue());
		System.out.println(patientName.getMiddleInitialOrName().getValue());
		System.out.println(adtMsg.getPID().getSex().getValue());
		System.out.println(adtMsg.getPID().getDateOfBirth().getTimeOfAnEvent());
		try {
			System.out.println(adtMsg.getPID().getDateOfBirth().getTimeOfAnEvent().getDay());
			System.out.println(adtMsg.getPID().getDateOfBirth().getTimeOfAnEvent().getMonth());
			System.out.println(adtMsg.getPID().getDateOfBirth().getTimeOfAnEvent().getYear());
		} catch (DataTypeException e) {
			e.printStackTrace();
		}
		
		System.out.println("");
		System.out.println(adtMsg.getDG1().getDiagnosisDRGType().getValue());
		
		System.out.println("");
		adtMsg.getEVN();
		
		System.out.println("");
		adtMsg.getPR1().getProcedureType();
		
		System.out.println("");
		adtMsg.getPV1().getAmbulatoryStatus();
		
		System.out.println("");
		adtMsg.getUB1();
		
		System.out.println("");
		adtMsg.getUB2();
	}
}