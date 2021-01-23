package de.lukaspanni.opensourcestats.ui.progress;


import com.lukaspanni.opensourcestats.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.lukaspanni.opensourcestats.data.ContributionCountChange;
import de.lukaspanni.opensourcestats.ui.StringResourceAccess;

public class MotivationMessageFactory {

    private StringResourceAccess resourceAccess;
    private static final List<Integer> keepOnMessages;
    private static final List<Integer> workHarderMessages;

    static {
        List<Integer> koList = new ArrayList<>();
        koList.add(R.string.keep_on_message_1);
        koList.add(R.string.keep_on_message_2);
        koList.add(R.string.keep_on_message_3);
        keepOnMessages = Collections.unmodifiableList(koList);
        List<Integer> whList = new ArrayList<>();
        whList.add(R.string.work_harder_message_1);
        whList.add(R.string.work_harder_message_2);
        whList.add(R.string.work_harder_message_3);
        workHarderMessages = Collections.unmodifiableList(whList);
    }

    public MotivationMessageFactory(StringResourceAccess resourceAccess) {
        this.resourceAccess = resourceAccess;
    }

    public MotivationMessage createMotivationMessage(ContributionCountChange change) {
        MotivationMessage.MOTIVATION_TYPE type;
        if (change.isPositive()) {
            type = MotivationMessage.MOTIVATION_TYPE.KEEP_ON;
        } else {
            type = MotivationMessage.MOTIVATION_TYPE.WORK_HARDER;
        }

        return new MotivationMessage(getRandomMessageString(type), type);
    }

    private String getRandomMessageString(MotivationMessage.MOTIVATION_TYPE type) {
        //Load Random Message from Resources based on type
        int id;
        Random random = new Random();
        if (type == MotivationMessage.MOTIVATION_TYPE.KEEP_ON) {
            int index = random.nextInt(keepOnMessages.size());
            id = keepOnMessages.get(index);
        } else {
            int index = random.nextInt(workHarderMessages.size());
            id = workHarderMessages.get(index);
        }
        return resourceAccess.getStringResource(id);
    }
}
