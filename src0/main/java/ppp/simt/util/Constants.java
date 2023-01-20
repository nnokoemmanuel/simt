package ppp.simt.util;

public  final  class Constants {

	public static final String NORMAL_LOG_DIR="Normal/";
	public static final String FAILED_CONNEXION_LOG_DIR="Connexion/";
	public static final String ACCESS_DENIED_LOG_DIR="Access/";
	public static final String EXCEPTION_LOG_DIR="Exception/";
    public static final String ROOT_LOOG_DIR="/var/log/simt/";
    public static final String COOKIE_LANGUAGE_NAME="simtLanguage";
    
    public static final int FIRST_YEAR_DEPLOY=2020;
    
    
    /**
     * LES DIFFERENTS ROLES
     */
    
    public static final String ROLE_ADMIN="ROLE_ADMIN";
    public static final String ROLE_DELEGUE="ROLE_DELEGUE";
    public static final String ROLE_MONITEUR="ROLE_MONITEUR";
    public static final String ROLE_DEC="ROLE_DEC";
    public static final String ROLE_DTR="ROLE_DTR";
    public static final String ROLE_SUPER_ADMIN="ROLE_SUPER_ADMIN";
    public static final String ROLE_DELEGUE_DEPARTEMENTAL="ROLE_DELEGUE_DEPARTEMENTAL";


    /**
     * LES STATUS DES APPLICATION POUR IMPRESSION DES OUTSLIPS
     */
    public static final int applicationStatusPrinted = 6;
    public static final int applicationStatusDelivered = 9;
    public static final int applicationStatusRejected = 10;
    public static final String SECURITY_TOKEN="310ec4e6213b134f6c2813bed00192195a756ee0"; // (sha1 notification)
}