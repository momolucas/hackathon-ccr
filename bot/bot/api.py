import requests
import ujson as json

BASEURL = "http://3.136.158.129:8080"


def get_user_data(phone):

    url = BASEURL + "/api/driver/list"
    try:
        response = requests.request("GET", url).json()
        for user in response:
            if user["phoneNumber"] == phone:
                return user
    except EnvironmentError:
        return {"name": "Amigo Caminhoneiro"}
    return {"name": "Amigo Caminhoneiro"}


def send_abastecimento(phone, litros):

    url = BASEURL + "/api/diario/abastecimento/" + phone
    payload = {"litros": litros}
    requests.request("POST", url, headers={'Content-Type': 'application/json'}, data=json.dumps(payload))


def send_inicio_viagem(phone, odometroValue):

    url = BASEURL + "/api/diario/abastecimento/" + phone
    payload = {"odometroValue": odometroValue}
    requests.request("POST", url, headers={'Content-Type': 'application/json'}, data=json.dumps(payload))


def send_fim_viagem(phone, odometroValue):

    url = BASEURL + "/api/diario/abastecimento/" + phone
    payload = {"odometroValue": odometroValue}
    requests.request("POST", url, headers={'Content-Type': 'application/json'}, data=json.dumps(payload))


def send_checklist(phone, checklist):

    url = BASEURL + "/api/diario/checklist/" + phone
    requests.request("POST", url, headers={'Content-Type': 'application/json'}, data=json.dumps(checklist))


def send_financeiro(phone, data):

    url = BASEURL + "/api/financeiro/" + phone
    requests.request("POST", url, headers={'Content-Type': 'application/json'}, data=json.dumps(data))


def send_pause(phone):
    url = "http://3.136.158.129:8080/api/diario/pause/" + phone
    requests.request("POST", url)


def send_eat(phone):
    url = "http://3.136.158.129:8080/api/diario/eat/" + phone
    requests.request("POST", url)
