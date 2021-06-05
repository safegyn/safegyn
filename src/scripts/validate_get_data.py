import time

import gspread
from collections import defaultdict
import json
import shelve

WORKSHEET_INDEX = shelve.open("WORKSHEET_INDEX")

ERROR_CORRECTION_MAP = {
    "standard consultation fees (inr)": "standard consultation fees"
}

SHEET_EXCLUSIONS = {
    "QCategories"
}

PRIMARY_TITLES = {
    "will they look for solutions to your pain and illness without advocating marriage/pregnancy?",
    "office tel. no.",
    "are they up-do-date in the field, aware of terms like endometriosis, sex toys, gender dysphoria and ptsd triggers?",
    "will they answer all your questions without making you feel immoral, ignorant, helpless or a nuisance?",
    "will they discuss masturbation positively and non-judgementally?",
    "will they frankly and without squeamishness advocate for your sexual satisfaction and pleasure?",
    "will they discuss choosing to stay celibate or child-free positively and non-judgementally?",
    "age-range",
    "will they respect the importance you do or don't place on the condition of your fertility?",
    "do they keep their office hygienic, clean, private and welcoming?",
    "is their office wheelchair accessible?",
    "office address",
    "will they discuss gender dysphoria related medical issues positively and non-judgementally?",
    "about the reccer",
    "will they make trans men, trans women and other folks who don't identify as cis female feel welcomed?",
    "will they prescribe, monitor and support pill-based abortions?",
    "will they respect your choice to keep or abort a pregnancy, regardless of your marital status?",
    "office hours",
    "will they respect your dietary choices?",
    "will they discuss queer sexuality (lesbian, bisexual, ace and others) positively and non-judgementally?",
    "will they discuss the sexuality of folks with mental illnesses without  judgement, and accommodate their needs while prescribing treatment?",
    "will they support your choice of non-traditional birthing techniques?",
    "will they respect your physical and emotional boundaries if you say you are feeling uncomfortable or in pain?",
    "will they make folks from an oppressed religion, race, ethnicity or caste feel welcomed?",
    "will they seek and respect your decision on any surgical/invasive procedure during delivery?",
    "name",
    "will they discuss menstrual hygiene choices including cups and tampons without moral judgements?",
    "will they discuss the sexuality of folks with physical disabilities without  judgement, and accommodate their needs while prescribing treatment?",
    "will they respect the importance you do or don't place on the condition of your hymen?",
    "will they work with your desire for alternative therapies and medicine systems?",
    "will they respectfully and non-judgmentally ask about and accept your sexual activity and history, regardless of marital status?",
    "will they prescribe you the morning after pill without judgement or hesitation?",
    "will they routinely screen for and keep you informed about cervical, breast and ovarian cancers?",
    "will they welcome survivors of incest, sexual and domestic abuse, and discuss healing from rape and sexual trauma sensitively and respectfully?",
    "will they support single parenting, surrogacy and non-normative parenting choices?",
    "will they discuss infertility treatment options positively and non-judgementally?",
    "other disability-accessibility",
    "will they keep your information private, even from your parents or partner?",
    "will they make poor and/or illiterate folks feel welcomed?",
    "will they examine your body thoroughly and professionally without making you feel ugly, embarrassed, ashamed or uncomfortable?",
    "will they accept criticism of their patient-interaction technique?",
    "will they advocate for your legal rights to sexual and medical autonomy if challenged by your family members/spouse/employers?",
    "reccer's testimonial",
    "will they be considerate of your financial burden and not prescribe unnecessary tests to pad their wallet?",
    "are you aware of a redressal mechanism to report sexual harassment or medical malpractice by this gynaecologist?",
    "will they welcome folks with aids or other sexually transmittable diseases?",
    "language(s) spoken",
    "gender",
    "will they refuse - even under pressure - to illegally reveal a foetus’s sex?",
    "what forms of payment do they accept?",
    "will they discuss kink, polyamoury, and other forms of sexual expression without embarrassment or censure?",
    "will they discuss sex work positively and non-judgementally?",
    "will they treat you as autonomous, without demanding permission/consent from parents or partner?",
    "will they support children and teenagers regardless of their sexual history and place their well-being ahead of their guardian’s morality?",
    "are they equipped for surgical abortions and post-termination monitoring and care?",
    "will they positively and non-judgementally respect your choice to be accompanied by someone who is not a biological parent or husband?",
    "will they give you options for birth control without advocating abstinence?",
    "standard consultation fees",
    "will they welcome your desired companion(s) into their delivery rooms?",
    "will they be able to refer you to non-judgemental and competent mental health professionals, endocrinologists and other allied medical practitioners?"
}


def check_sheet_titles():
    google_client = gspread.service_account()
    spreadsheet = google_client.open("Copy of Crowdsourced List of Gynaecologists We Can Trust In India")

    # # Prints all the titles in the first sheet
    # primary_titles = set(spreadsheet.get_worksheet(1).col_values(1))
    # primary_titles = {str(datum).strip().lower() for datum in primary_titles}
    # print(str(len(primary_titles)), "Primary Titles:")
    # for index, t in enumerate(sorted(primary_titles)):
    #     print("formalToInformalTitleMap.put(\"title" + str(index) + "\", \"" + t + "\");", sep="")

    get_check_titles(spreadsheet, PRIMARY_TITLES)


def get_check_titles(spreadsheet, worksheet_title_values):
    for worksheet in spreadsheet:
        if worksheet.title in SHEET_EXCLUSIONS:
            continue

        secondary_titles = get_corrected_titles_to_compare(worksheet)

        title_not_in_secondary = worksheet_title_values.difference(secondary_titles)
        title_not_in_primary = secondary_titles.difference(worksheet_title_values)
        if title_not_in_secondary:
            print("Titles not in secondary for ", worksheet.title, ":", title_not_in_secondary)
        if title_not_in_primary:
            print("Titles not in primary for ", worksheet.title, ":", title_not_in_primary)


def get_corrected_titles_to_compare(worksheet):
    titles_to_check = worksheet.col_values(1)
    titles_to_check = {str(datum).strip().lower() for datum in titles_to_check}
    if "" in titles_to_check: titles_to_check.remove("")

    for title in titles_to_check:
        if title in ERROR_CORRECTION_MAP:
            corrected_version = ERROR_CORRECTION_MAP[title]
            titles_to_check.remove(title)
            titles_to_check.add(corrected_version)

    return titles_to_check


def get_sheet_data():
    google_client = gspread.service_account()
    spreadsheet = google_client.open("Copy of Crowdsourced List of Gynaecologists We Can Trust In India")

    if "last_success" not in WORKSHEET_INDEX:
        WORKSHEET_INDEX["last_success"] = -1
        WORKSHEET_INDEX["result"] = defaultdict(list)

    print("Starting from :" + str(WORKSHEET_INDEX["last_success"]))

    result_map = WORKSHEET_INDEX["result"]
    for worksheet_count in range(WORKSHEET_INDEX["last_success"] + 1, len(spreadsheet.worksheets())):
        worksheet = spreadsheet.worksheets()[worksheet_count]
        if worksheet.title in SHEET_EXCLUSIONS or worksheet.title in WORKSHEET_INDEX["result"]:
            continue

        print(worksheet.title)

        try:
            row_list = worksheet.get_all_values()
        except Exception:
            print("Waiting 120 seconds")
            time.sleep(120)
            row_list = worksheet.get_all_values()

        record_list = list(map(list, zip(*row_list)))
        title_records = record_list[0]

        for i in range(1, len(record_list)):
            record_dict = dict(zip(title_records, record_list[i]))
            result_map[worksheet.title].append(record_dict)

        WORKSHEET_INDEX["result"] = result_map
        WORKSHEET_INDEX["last_success"] = worksheet_count

    # Serializing json
    with open("records.json", 'w') as json_file:
        json.dump(WORKSHEET_INDEX["result"], json_file)


if __name__ == "__main__":
    #check_sheet_titles()
    get_sheet_data()

