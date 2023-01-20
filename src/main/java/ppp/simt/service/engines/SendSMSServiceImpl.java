package ppp.simt.service.engines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.service.production.PrintReportService;
import ppp.simt.util.Logger_;


@Service
public class SendSMSServiceImpl implements SendSMSService {


    @Autowired
    private PrintReportService printReportService;

    @Autowired
	private Logger_ logger_;

	public void sendSMS(int destinationnumber, String message){



       /* $sendAfter = date_create("00:00:00.000000");

        $now = date_create("0000-00-00 00:00:00.000000");

        $now = date('Y-m-d h:i:s',time()-3600);
        $now = date_create($now);

        //die($now->format(('d:m:Y')));
        $sendBefore = date_create("23:59:59.000000");

        $em = $this->container->get('doctrine')->getManager('smsd');
        $outMessage = new Outbox();
        $outMessage->setDestinationnumber($destinationnumber);
        $outMessage->setRelativevalidity(255);
        $outMessage->setTextdecoded($message);
        $outMessage->setSendbefore($sendBefore);
        $outMessage->setSendafter($sendAfter);
        $outMessage->setCoding("Default_No_Compression");
        //rand(1, 100);
        $outMessage->setCreatorid("PPP");
        $outMessage->setSenderid("MINT") ;
        $outMessage->setUdh(null);
        $outMessage->setClass(-1);
        $outMessage->setMultipart('false');
        $outMessage->setSendingdatetime($now);
        $outMessage->setSendingtimeout($now);
        $outMessage->setDeliveryreport("default");
        $outMessage->setText(null);


        $em->persist($outMessage);
        $em->flush();


        */
	}


}
