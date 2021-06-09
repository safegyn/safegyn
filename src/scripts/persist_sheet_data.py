import json
import requests

ERROR_CORRECTION_MAP = {
    "STANDARD CONSULTATION FEES (INR)": "STANDARD CONSULTATION FEES"
}

if __name__ == '__main__':
    with open("records.json") as records:
        json_from_file = json.load(records)

    for city in json_from_file:
        print(city)
        # Creating a blank copy to allow modifications + normalization
        json_clone = dict()

        json_clone[city] = []
        for review in json_from_file[city]:
            json_clone_map = dict()
            for title in review:
                normalized_title = title.strip().upper()
                if not normalized_title: continue
                if normalized_title in ERROR_CORRECTION_MAP: normalized_title = ERROR_CORRECTION_MAP[normalized_title]
                json_clone_map[normalized_title] = review[title]
            json_clone[city].append(json_clone_map)

        cookies = {'JSESSIONID': ''}
        headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}

        response = requests.post("http:www.safegyn.com/safegyn/api/admin/migration/reviews", json.dumps(json_clone),
                                 cookies=cookies, headers=headers)

        if response.status_code != 200:
            print("Stopped at City:" + city + " with HTTP-status:" + str(response.status_code))
            print(response.text)
            break
