package StarBreakerMod.minions.ai;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.relics.KakaDogTag;

public class KakaAIFactory {
    public enum KakaAIType {
        DEFAULT,
    }

    public AbstractKakaAI getAI(KakaDogTag dogTag, KakaAIType aiType) {
        if(aiType == null)
            return null;

        StarBreakerMod.logger.info("generate AI:", aiType, dogTag);
        switch (aiType) {
            case DEFAULT:
                return new DefaultKakaAI(dogTag);
            default:
                return null;
        }
    }
}
