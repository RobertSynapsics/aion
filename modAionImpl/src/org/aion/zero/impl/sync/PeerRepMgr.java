package org.aion.zero.impl.sync;

import org.aion.base.db.IByteArrayKeyValueDatabase;
import org.aion.log.AionLoggerFactory;
import org.aion.log.LogEnum;
import org.aion.mcf.ds.ObjectDataSource;
import org.aion.zero.impl.db.PeerReputationSerializer;
import org.aion.zero.impl.types.PeerReputation;
import org.slf4j.Logger;

public class PeerRepMgr {

    private static final Logger LOG = AionLoggerFactory.getLogger(LogEnum.DB.name());

    public IByteArrayKeyValueDatabase peerManagerDatabase;
    ObjectDataSource<PeerReputation> peers;

    public PeerRepMgr(IByteArrayKeyValueDatabase peerRepDb) {
        this.peerManagerDatabase = peerRepDb;
        this.peers = new ObjectDataSource<PeerReputation>(peerRepDb, PeerReputationSerializer.serializer);
    }

    public double getPeerReputationValue(byte[] nodeId) {
        PeerReputation peerRep = peers.get(nodeId);

        if (peerRep == null) {
            peerRep = createNewPeerReputation(nodeId);
        }

        return peerRep.getReputation();
    }

    public double getPeerConnections(byte[] nodeId) {
        PeerReputation peerRep = peers.get(nodeId);

        if (peerRep == null) {
            peerRep = createNewPeerReputation(nodeId);
        }
        return peerRep.getPeersConnected();
    }

    public PeerReputation getPeerReputation(byte[] nodeId) {
        PeerReputation peerRep = peers.get(nodeId);

        if (peerRep == null) {
            peerRep = createNewPeerReputation(nodeId);
        }

        return peerRep;
    }

    private PeerReputation createNewPeerReputation(byte[] nodeId) {
        //TODO @Robert check this is debugging ok
        LOG.debug("Creating a default peer reputation profile for node: " + new String(nodeId));
        PeerReputation peerRep;
        peerRep = new PeerReputation();
        peerRep.setNodeId(nodeId);
        //save the new reputation
        peers.put(nodeId, peerRep);
        return peerRep;
    }
}
