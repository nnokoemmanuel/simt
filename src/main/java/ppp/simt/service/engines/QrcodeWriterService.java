package ppp.simt.service.engines;

import java.io.IOException;

public interface QrcodeWriterService {
    public Process qrCodeGenerate(int applicationId);
    public String encryptInfo( String applicantInformation);
	String addVersionAndEncryptionSignature(String encryptedApplicantInformation);
	public 	String saveQrcode(int applicantId,String finalApplicanInformation,String rootQrcodefolder) throws IOException;
	public String generateApplicantData(int applicationId);
	public String generateEncryptedQrcode(int studentSessionId,boolean isForProfessionalCard) throws IOException;
	public String saveQrcodeCard(int applicantId,String finalApplicanInformation,String rootQrcodefolder) throws IOException;


}
