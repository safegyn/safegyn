package org.safegyn.model.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.Getter;
import lombok.Setter;
import org.safegyn.db.entity.AbstractEntity;

@Getter
@Setter
public class TitleMap extends AbstractEntity {

    private static final BiMap<String, String> informalToFormalTitleMap = HashBiMap.create();

    static {
        informalToFormalTitleMap.put("name", "name");
        informalToFormalTitleMap.put("gender", "gender");
        informalToFormalTitleMap.put("age-range", "ageRange");
        informalToFormalTitleMap.put("office address", "officeAddress");
        informalToFormalTitleMap.put("office tel. no.", "telNo");
        informalToFormalTitleMap.put("standard consultation fees (inr)", "consultationFees");
        informalToFormalTitleMap.put("what forms of payment do they accept?", "paymentForms");
        informalToFormalTitleMap.put("office hours", "officeHours");
        informalToFormalTitleMap.put("language(s) spoken", "languagesSpoken");

        informalToFormalTitleMap.put("about the reccer", "aboutReviewer");
        informalToFormalTitleMap.put("reccer's testimonial", "testimonial");

        informalToFormalTitleMap.put("will they keep your information private, even from your parents or partner?", "q1");
        informalToFormalTitleMap.put("will they treat you as autonomous, without demanding permission/consent from parents or partner?", "q2");

        informalToFormalTitleMap.put("is their office wheelchair accessible?", "q3");
        informalToFormalTitleMap.put("other disability-accessibility", "q4");
        informalToFormalTitleMap.put("will they make poor and/or illiterate folks feel welcomed?", "q5");
        informalToFormalTitleMap.put("will they be considerate of your financial burden and not prescribe unnecessary tests to pad their wallet?", "q6");
        informalToFormalTitleMap.put("will they make folks from an oppressed religion, race, ethnicity or caste feel welcomed?", "q7");
        informalToFormalTitleMap.put("will they make trans men, trans women and other folks who don't identify as cis female feel welcomed?", "q8");
        informalToFormalTitleMap.put("will they welcome survivors of incest, sexual and domestic abuse, and discuss healing from rape and sexual trauma sensitively and respectfully?", "q9");
        informalToFormalTitleMap.put("will they welcome folks with aids or other sexually transmittable diseases?", "q10");
        informalToFormalTitleMap.put("will they discuss the sexuality of folks with physical disabilities without  judgement, and accommodate their needs while prescribing treatment?", "q11");
        informalToFormalTitleMap.put("will they discuss the sexuality of folks with mental illnesses without  judgement, and accommodate their needs while prescribing treatment?", "q12");
        informalToFormalTitleMap.put("will they discuss sex work positively and non-judgementally?", "q13");

        informalToFormalTitleMap.put("will they positively and non-judgementally respect your choice to be accompanied by someone who is not a biological parent or husband?", "q14");
        informalToFormalTitleMap.put("will they discuss gender dysphoria related medical issues positively and non-judgementally?", "q15");
        informalToFormalTitleMap.put("will they discuss queer sexuality (lesbian, bisexual, ace and others) positively and non-judgementally?", "q16");
        informalToFormalTitleMap.put("will they discuss kink, polyamoury, and other forms of sexual expression without embarrassment or censure?", "q17");
        informalToFormalTitleMap.put("will they support children and teenagers regardless of their sexual history and place their well-being ahead of their guardian’s morality?", "q18");
        informalToFormalTitleMap.put("will they discuss masturbation positively and non-judgementally?", "q19");
        informalToFormalTitleMap.put("will they discuss menstrual hygiene choices including cups and tampons without moral judgements?", "q20");
        informalToFormalTitleMap.put("will they respect the importance you do or don't place on the condition of your hymen?", "q21");
        informalToFormalTitleMap.put("will they respectfully and non-judgmentally ask about and accept your sexual activity and history, regardless of marital status?", "q22");
        informalToFormalTitleMap.put("will they give you options for birth control without advocating abstinence?", "q23");
        informalToFormalTitleMap.put("will they prescribe you the morning after pill without judgement or hesitation?", "q24");

        informalToFormalTitleMap.put("do they keep their office hygienic, clean, private and welcoming?", "q25");
        informalToFormalTitleMap.put("will they look for solutions to your pain and illness without advocating marriage/pregnancy?", "q26");
        informalToFormalTitleMap.put("are they equipped for surgical abortions and post-termination monitoring and care?", "q27");
        informalToFormalTitleMap.put("will they prescribe, monitor and support pill-based abortions?", "q28");
        informalToFormalTitleMap.put("will they refuse - even under pressure - to illegally reveal a foetus’s sex?", "q29");
        informalToFormalTitleMap.put("are they up-do-date in the field, aware of terms like endometriosis, sex toys, gender dysphoria and ptsd triggers?", "q30");
        informalToFormalTitleMap.put("will they routinely screen for and keep you informed about cervical, breast and ovarian cancers?", "q31");
        informalToFormalTitleMap.put("will they be able to refer you to non-judgemental and competent mental health professionals, endocrinologists and other allied medical practitioners?", "q32");
        informalToFormalTitleMap.put("will they accept criticism of their patient-interaction technique?", "q33");
        informalToFormalTitleMap.put("are you aware of a redressal mechanism to report sexual harassment or medical malpractice by this gynaecologist?", "q34");

        informalToFormalTitleMap.put("will they advocate for your legal rights to sexual and medical autonomy if challenged by your family members/spouse/employers?", "q35");
        informalToFormalTitleMap.put("will they respect your dietary choices?", "q36");
        informalToFormalTitleMap.put("will they work with your desire for alternative therapies and medicine systems?", "q37");
        informalToFormalTitleMap.put("will they frankly and without squeamishness advocate for your sexual satisfaction and pleasure?", "q38");
        informalToFormalTitleMap.put("will they discuss choosing to stay celibate or child-free positively and non-judgementally?", "q39");
        informalToFormalTitleMap.put("will they respect your choice to keep or abort a pregnancy, regardless of your marital status?", "q40");
        informalToFormalTitleMap.put("will they respect the importance you do or don't place on the condition of your fertility?", "q41");
        informalToFormalTitleMap.put("will they discuss infertility treatment options positively and non-judgementally?", "q42");
        informalToFormalTitleMap.put("will they support single parenting, surrogacy and non-normative parenting choices?", "q43");
        informalToFormalTitleMap.put("will they support your choice of non-traditional birthing techniques?", "q44");
        informalToFormalTitleMap.put("will they welcome your desired companion(s) into their delivery rooms?", "q45");
        informalToFormalTitleMap.put("will they seek and respect your decision on any surgical/invasive procedure during delivery?", "q46");
        informalToFormalTitleMap.put("will they examine your body thoroughly and professionally without making you feel ugly, embarrassed, ashamed or uncomfortable?", "q47");
        informalToFormalTitleMap.put("will they answer all your questions without making you feel immoral, ignorant, helpless or a nuisance?", "q48");
        informalToFormalTitleMap.put("will they respect your physical and emotional boundaries if you say you are feeling uncomfortable or in pain?", "q49");
    }

}
