package net.voldrich.smscsim.server;

import org.joda.time.DateTime;

import net.voldrich.smscsim.spring.auto.SmppSessionManager;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.type.Address;

public class DeliveryReceiptRecord extends DelayedRecord {
	private final Address sourceAddress;
	private final Address destinationAddress;
	private final long messageId;
	private final DateTime submitDate;

	public DeliveryReceiptRecord(SmppSession session, SubmitSm pduRequest, long messageId) {
		super(session);
		this.sourceAddress = pduRequest.getDestAddress();
		this.destinationAddress = pduRequest.getSourceAddress();
		this.messageId = messageId;
		this.submitDate = new DateTime();
	}

	public Address getSourceAddress() {
		return sourceAddress;
	}

	public Address getDestinationAddress() {
		return destinationAddress;
	}

	public long getMessageId() {
		return messageId;
	}

	public DateTime getSubmitDate() {
		return submitDate;
	}

	@Override
	public SmppSession getUsedSession(SmppSessionManager sessionManager) {
		String systemId = getSession().getConfiguration().getSystemId();
		return sessionManager.getNextServerSession(systemId);
	}

	@Override
	public PduRequest getRequest(int sequenceNumber) throws Exception {
		return SmppPduUtils.createDeliveryReceipt(this, sequenceNumber);
	}

}
