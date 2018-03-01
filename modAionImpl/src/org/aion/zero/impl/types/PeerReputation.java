package org.aion.zero.impl.types;

import org.aion.rlp.RLP;
import org.aion.rlp.RLPItem;
import org.aion.rlp.RLPList;

import java.nio.ByteBuffer;

public class PeerReputation {

    private static final double DEFAULT_REPUTATION = 1;
    private static final double DEFAULT_PEERS_CONNECTED = 8;
    private static final double DEFAULT_NEW_BLOCK_PROPAGATION = 1;
    private static final double DEFAULT_LATENCY = 1;
    private static final double DEFAULT_SYNC_RATE = 1;
    private static final double DEFAULT_TRANSACTION_VALIDITY_RATE = 1;
    private static final double DEFAULT_UPTIME = 1;

    private byte[] nodeId;

    /**
     * computed generic reputation value
     */
    private double reputation;

    /**
     * number of peers connected to this node
     */
    private double peersConnected;

    private double newBlockPropagation;

    /**
     * Latency in ms
     */
    private double latency;

    private double syncRate;

    /**
     * Transaction validity rate in % between 0.0 to 1.0
     */
    private double transactionValidityRate;

    /**
     * Uptime with values between 0.0(0% of the time online) and 1.0(100% of the time online)
     */
    private double upTime;

    /**
     * Getters
     */
    public byte[] getNodeId() {
        return nodeId;
    }

    public double getReputation() {
        return reputation;
    }

    public double getPeersConnected() {
        return peersConnected;
    }

    public double getNewBlockPropagation() {
        return newBlockPropagation;
    }

    public double getLatency() {
        return latency;
    }

    public double getSyncRate() {
        return syncRate;
    }

    public double getTransactionValidityRate() {
        return transactionValidityRate;
    }

    public double getUpTime() {
        return upTime;
    }

    /**
     * Setters
     */
    public void setNodeId(byte[] nodeId) {
        this.nodeId = nodeId;
    }

    public void setReputation(double reputation) {
        this.reputation = reputation;
    }

    public void setPeersConnected(double peersConnected) {
        this.peersConnected = peersConnected;
    }

    public void setNewBlockPropagation(double newBlockPropagation) {
        this.newBlockPropagation = newBlockPropagation;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public void setSyncRate(double syncRate) {
        this.syncRate = syncRate;
    }

    public void setTransactionValidityRate(double transactionValidityRate) {
        this.transactionValidityRate = transactionValidityRate;
    }

    public void setUpTime(double upTime) {
        this.upTime = upTime;
    }

    /**
     * Creates a default filled Peer Reputation
     */
    public PeerReputation(){
        this.nodeId = new byte[36];
        this.reputation = DEFAULT_REPUTATION;
        this.peersConnected = DEFAULT_PEERS_CONNECTED;
        this.newBlockPropagation = DEFAULT_NEW_BLOCK_PROPAGATION;
        this.latency = DEFAULT_LATENCY;
        this.syncRate = DEFAULT_SYNC_RATE;
        this.transactionValidityRate = DEFAULT_TRANSACTION_VALIDITY_RATE;
        this.upTime = DEFAULT_UPTIME;
    }

    public PeerReputation(byte[] rlp) {
        RLPList params = RLP.decode2(rlp);
        RLPList PeerReputation = (RLPList) params.get(0);

        RLPItem nodeIdRLP = (RLPItem) PeerReputation.get(0);
        RLPItem reputationRLP = (RLPItem) PeerReputation.get(1);
        RLPItem peersConectedRLP = (RLPItem) PeerReputation.get(2);
        RLPItem newBlockPropagationRLP = (RLPItem) PeerReputation.get(3);
        RLPItem latencyRLP = (RLPItem) PeerReputation.get(4);
        RLPItem syncRateRLP = (RLPItem) PeerReputation.get(5);
        RLPItem transactionValidityrateRLP = (RLPItem) PeerReputation.get(6);
        RLPItem upTimeRLP = (RLPItem) PeerReputation.get(7);

        this.nodeId = nodeIdRLP.getRLPData();
        this.reputation = toDouble(reputationRLP.getRLPData());
        this.peersConnected = toDouble(peersConectedRLP.getRLPData());
        this.newBlockPropagation = toDouble(newBlockPropagationRLP.getRLPData());
        this.latency = toDouble(latencyRLP.getRLPData());
        this.syncRate = toDouble(syncRateRLP.getRLPData());
        this.transactionValidityRate = toDouble(transactionValidityrateRLP.getRLPData());
        this.upTime = toDouble(upTimeRLP.getRLPData());
    }

    public byte[] getEncoded() {
        byte[] nodeIdRLP = RLP.encodeElement(nodeId);
        byte[] reputationRLP = RLP.encodeElement(toByteArray(reputation));
        byte[] peersConectedRLP = RLP.encodeElement(toByteArray(peersConnected));
        byte[] newBlockPropagationRLP = RLP.encodeElement(toByteArray(newBlockPropagation));
        byte[] latencyRLP = RLP.encodeElement(toByteArray(latency));
        byte[] syncRateRLP = RLP.encodeElement(toByteArray(syncRate));
        byte[] transactionValidityrateRLP = RLP.encodeElement(toByteArray(transactionValidityRate));
        byte[] upTimeRLP = RLP.encodeElement(toByteArray(upTime));

        byte[] rlpEncoded = RLP.encodeList(nodeIdRLP, reputationRLP, peersConectedRLP, newBlockPropagationRLP
                                          ,latencyRLP,  syncRateRLP, transactionValidityrateRLP, upTimeRLP);
        return rlpEncoded;
    }

    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
