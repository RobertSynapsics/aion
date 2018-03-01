package org.aion.zero.impl.db;

import org.aion.mcf.ds.Serializer;
import org.aion.zero.impl.types.PeerReputation;

public class PeerReputationSerializer {
    public final static Serializer<PeerReputation, byte[]> serializer = new Serializer<PeerReputation, byte[]>() {
        @Override
        public byte[] serialize(PeerReputation block) {
            return block.getEncoded();
        }

        @Override
        public PeerReputation deserialize(byte[] bytes) {
            return new PeerReputation(bytes);
        }
    };
}
