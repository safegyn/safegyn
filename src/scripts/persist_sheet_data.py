import json
import requests

ERROR_CORRECTION_MAP = {
    "STANDARD CONSULTATION FEES": "STANDARD CONSULTATION FEES (INR)"
}

if __name__ == '__main__':
    with open("records.json") as records:
        json_dict = json.load(records)

    print(len(json_dict))

    for e in json_dict:
        json_in = dict()
        json_in[e] = json_dict[e]

        for x in json_in[e]:
            if "" in x: x.pop("")

            for error in ERROR_CORRECTION_MAP:
                if error in x:
                    print("Error:" + error + ". " + x[error] + " in map")
                    value = json_in[e][x]
                    x.pop(error)
                    x[ERROR_CORRECTION_MAP[error]] = value
                    print("Correction 1:" + x[error])
                    print("Correction 2:" + x[ERROR_CORRECTION_MAP[error]])

        cookies = {'JSESSIONID': 'node01ipqrjeoc4w8ce5jf0x1d2f2q0.node0; _ga=GA1.1.820187819.1619887025'}
        headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
        response = requests.post("http://localhost:9090/safegyn/api/admin/reviews", json.dumps(json_in), cookies=cookies, headers=headers)
        if response.status_code != 200:
            print(e)
            print(response.text)
            #print(json.dumps(json_in))
            break