package usp.ime.gclib.example;

import usp.ime.gclib.device.Device;
import usp.ime.gclib.hit.ShootingRestrictions;
import usp.ime.gclib.hit.TargetRestrictions;
import usp.ime.gclib.net.communication.ReceiveListener;
import usp.ime.gclib.net.protocol.AppProtocol;
import usp.ime.gclib.net.protocol.CommunicationSocket;
import usp.ime.gclib.net.protocol.EProtocolMessages;
import usp.ime.gclib.net.protocol.EProtocolTranspLayer;
import usp.ime.gclib.net.protocol.ESendTo;
import usp.ime.gclib.net.protocol.LibConfigurationObject;
import usp.ime.gclib.net.protocol.ProtocolGEOACKInformation;
import usp.ime.gclib.net.protocol.ProtocolGEOMSGInformation;
import usp.ime.gclib.net.protocol.ProtocolInformation;
import usp.ime.gclib.net.protocol.ProtocolLIBCONFIGInformation;
import usp.ime.gclib.sensor.location.DeviceLocation;
import usp.ime.gclib.sensor.orientation.DeviceGyroscopeOrientation;
import android.content.Context;


public class TesteClasses implements ReceiveListener{

	private static Context context;
	
	public void onReceiveGEOMSG(ProtocolGEOMSGInformation appInfo) {
		System.out.println("NICK do cara: " + appInfo.getDeviceSrc().getNick());
	}
	
	public static void main(String[] args) {
		System.out.println("Recebendo...");
		CommunicationSocket socket = new CommunicationSocket();
		TesteClasses listener = new TesteClasses();
		int ret = socket.acceptListener(listener);
		System.out.println("Recebimento " + ret +"\n");
		
		System.out.println("Enviando...");
		String s = "Mensagem";
		CommunicationSocket sndSocket = new CommunicationSocket();
		Device dev = new Device("nick");
		
		DeviceGyroscopeOrientation devOr = new DeviceGyroscopeOrientation(dev);
		DeviceLocation devLoc = new DeviceLocation(dev);
		
		devLoc.enableLocationListener(context, 0, 0);
		devOr.enableSensorListener(context);
		
	 	AppProtocol o = new AppProtocol(s.getBytes(), EProtocolMessages.GEOMSG, ESendTo.ALL, EProtocolTranspLayer.UDP);
	 	TargetRestrictions targetRest = new TargetRestrictions();
	 	ShootingRestrictions shootRest = new ShootingRestrictions();
	 	LibConfigurationObject libConfig = new LibConfigurationObject(targetRest, shootRest);
	 	ProtocolLIBCONFIGInformation pc = new ProtocolLIBCONFIGInformation(dev, o, libConfig);
		ret = sndSocket.sendMessage(o, pc);
		
		System.out.println("Envio " + ret);
		
	}

	public void onReceiveAPPDATA(ProtocolInformation appInfo) {
		
	}

	public void onReceiveONLINE(ProtocolInformation appInfo) {
		
	}

	public void onReceiveLIBCONFIG(ProtocolLIBCONFIGInformation appInfo) {
		
	}

	public void onReceiveGEOACK(ProtocolGEOACKInformation appInfo) {
		
	}

	public void onReceiveONLINEANSWER(ProtocolInformation appInfo) {
		
	}

}
