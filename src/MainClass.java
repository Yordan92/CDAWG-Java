
public class MainClass {


	public static void main(String[] args) {
		CDAWG cdawg = new CDAWG();
		String textForIngestion = "cocoafdfdg";
		cdawg.create(textForIngestion);

		System.out.println(cdawg.find(textForIngestion.substring(0, textForIngestion.length() )));
	}
}
