# -*- coding: utf-8 -*-

import datetime
import logging
import random
import requests
import ujson as json
from flask import Flask, request

import api

log = logging.getLogger('werkzeug')
log.setLevel(logging.ERROR)
app = Flask(__name__)
# Chave da api
app.secret_key = b'senacabulos54321'
# Lista de seções
sessions = {}


class Session:
    def __init__(self, phone, action):
        self.action = action
        self.last_update = datetime.datetime.now()


menu_text = '''
O que precisa para a viagem de hoje?

*1* - \U0001F4BB Diário de Bordo
*2* - \U0001F4B5 Controle Financeiro
*3* - \U00002753 Ajuda

Digite o *número* da opção:
'''

diario = '''
O que precisamos registrar?

*1* - \U000026FD Abastecimento
*2* - \U0001F6A6 Início de Viagem
*3* - \U0001F4A4 Fim de Viagem
*4* - \U0001F6D1 Pausa para descanso de 30 min
*5* - \U0001F37D Pausa para refeição de 1h
*6* - \U0001F69A Checklist do Caminhão

Digite o *número* da opção:
'''

financeiro = '''
Como posso te ajudar?

*1* - \U0001F4B8 Registrar Despesa
*2* - \U0001F4C8 Relatório Financeiro

Digite o *número* da opção:
'''

categorias = '''
*1* - Alimentação
*2* - Mecânica
*3* - Combustível
*4* - Pedágio
'''

help_text = '''
Esse é um assistente pessoal para auxiliar durante a viagem.
Posso ajudar lembrando de realizar atividades importantes, auxiliando no controle de despesas e prestação de contas, e ainda dar dicas de como ter uma rotina mais saudável.

*Como posso utilizar?*
Se você for autônomo, entre em contato com a nossa equipe para ser cadastrado.
Se você está vinculado à alguma empresa, verifique se ela possui cadastro para uso do assistente.

*Como funcionam os alertas?*
O assistente ajuda você a não esquecer de atividades como: Registro de início e fim de viagem, troca de óleo, cuidados com a saúde e com as finanças por exemplo.

*Posso compartilhar o contato do assistente?*
Claro! Quanto mais colegas estivermos ajudando, melhor!

*Preciso utilizar todo dia?*
As vantagens são muito maiores quando você utiliza de forma completa!

Digite *menu* para eu te ajudar de alguma forma.

'''


def check_message(data):
    phone = data[0]["Body"]["Info"]["RemoteJid"]
    try:
        text = data[0]["Body"]["Text"]
    except:
        # TODO: usar como regra para saber se tem evidencia
        text = "imagem"
        image = data[0]["image"]

    info = api.get_user_data(phone)
    name = info["name"].split(" ")[0]

    # Se a sessao ja esta ativa
    if(phone in sessions):

        print(info["name"] + ": " + sessions[phone].action["type"])

        if text in ["sair", "Sair"]:
            del sessions[phone]
            return

        if(sessions[phone].action["type"] == "next"):
            send_message(phone, menu_text)
            sessions[phone].action["type"] = "menu"
            return

        # Se tem sessao, encerra e começa novamente
        if text in ["menu", "Menu", "cancelar", "Cancelar"]:
            sessions[phone].action["type"] = "menu"
            send_message(phone, menu_text)
            return

        # Se estiver no menu
        if(sessions[phone].action["type"] == "menu"):

            # Se escolher a opção de diario de bordo
            if text == "1":
                send_message(phone, diario)
                sessions[phone].action["type"] = "diario"
                return

            elif text == "2":
                send_message(phone, financeiro)
                sessions[phone].action["type"] = "financeiro"
                return

            # Se escolher a opção de ajuda
            elif text == "3":
                send_message(phone, help_text)
                sessions[phone].action["type"] = "next"
                return

            else:
                send_message(phone, "Opção inválida, tente novamente.")
                return

        # Se estiver no diario
        if(sessions[phone].action["type"] == "diario"):

            # Se quiser registrar abastecimento
            if(text) == "1":
                send_message(phone, "Quantos litros foram?\nInsira o número sem vírgula, até 3 dígitos:")
                sessions[phone].action["type"] = "diario-abastecimento"
                return

            elif(text) == "2":
                send_message(phone, "Qual a quilometragem do odômetro?\nDigite os 6 dígitos:")
                sessions[phone].action["type"] = "quilometragem-inicio"
                return

            elif(text) == "3":
                send_message(phone, "Qual a quilometragem do odômetro?\nDigite os 6 dígitos:")
                sessions[phone].action["type"] = "quilometragem-fim"
                return

            elif(text) == "4":
                send_message(phone, "Pausa para descanço iniciada *" + name + "*, te chamo em 30 minutos!\nNão esqueça de usar o freio de parada.")
                if info["name"] != "Amigo Caminhoneiro":
                    api.send_pause(phone)
                sessions[phone].action["type"] = "next"
                return

            elif(text) == "5":
                send_message(phone, "Pausa para refeição iniciada *" + name + "*, te chamo em 1 hora!\nNão esqueça de usar o freio de parada.")
                if info["name"] != "Amigo Caminhoneiro":
                    api.send_eat(phone)
                sessions[phone].action["type"] = "next"
                return

            elif(text) == "6":
                send_message(phone, "*" + name + "*, vamos começar a conferência:")
                send_message(phone, "Já conferiu o óleo e a água? Envie uma foto do medidor de óleo, por favor.")
                sessions[phone].action["type"] = "checklist-pneus"
                sessions[phone].checklist = {}
                return

            else:
                send_message(phone, "Opção inválida, tente novamente.")
                return

        # Se acessar o modulo financeiro:
        if sessions[phone].action["type"] == "financeiro":
            if text == "1":
                send_message(phone, "Envie uma foto do comprovante, circulando o nome do local e o valor:")
                sessions[phone].action["type"] = "financeiro-comprovante"
                return
            if text == "2":
                send_message(phone, "Sem informações suficientes para um relatório. Continue registrando e logo podemos analisar melhor.")
                sessions[phone].action["type"] = "next"
                return

        # Se estiver coletando abastecimento:
        if sessions[phone].action["type"] == "diario-abastecimento":
            if info["name"] != "Amigo Caminhoneiro":
                api.send_abastecimento(phone, text)
            send_message(phone, "Registrado, boa viagem.")
            sessions[phone].action["type"] = "next"
            return

        # FINANCEIRO
        # coleta da evidencia de despesa
        if(sessions[phone].action["type"] == "financeiro-comprovante"):
            # TODO: Registrar evidencia agua e oleo
            if(text != "imagem"):
                send_message(phone, "Por favor, envie uma imagem.")
                return
            send_message(phone, "Qual a categoria desta despesa?\n" + categorias)
            sessions[phone].action["type"] = "financeiro-comprovante-final"
            return

        # final financeiro
        if(sessions[phone].action["type"] == "financeiro-comprovante-final"):
            # TODO: Registrar evidencia agua e oleo
            send_message(phone, "Despesa registrada.")
            api.send_financeiro(phone, {"category": random.randrange(1, 4), "cost": round(random.uniform(33.33, 66.66), 1)})
            sessions[phone].action["type"] = "next"
            return

        # QUILOMETRAGEM
        # coleta dados de quilometragem no inicio
        if(sessions[phone].action["type"] == "quilometragem-inicio"):
            if info["name"] != "Amigo Caminhoneiro":
                api.send_inicio_viagem(phone, text)
            send_message(phone, "Início de viagem registrado, *" + name + "*!\nUse o cinto de segurança e boa viagem!")
            sessions[phone].action["type"] = "next"
            return

        if(sessions[phone].action["type"] == "quilometragem-fim"):
            if info["name"] != "Amigo Caminhoneiro":
                api.send_fim_viagem(phone, text)
            send_message(phone, "Fim de viagem registrado, bom descanso *" + name + "*!")
            sessions[phone].action["type"] = "next"
            return

        # CHECKLIST
        # Checklist oleo e agua
        if(sessions[phone].action["type"] == "checklist-pneus"):
            if(text != "imagem"):
                send_message(phone, "Por favor, envie uma imagem.")
                return
            sessions[phone].checklist["medidorOleo"] = image
            send_message(phone, "Vamos conferir os pneus agora. Se tiver algum problema envie uma foto para registrarmos, senão, só me avisar que está OK!")
            sessions[phone].action["type"] = "checklist-farois"
            return

        # Checklist farois
        if(sessions[phone].action["type"] == "checklist-farois"):
            if(text != "imagem"):
                sessions[phone].checklist["pneus"] = "OK"
            else:
                sessions[phone].checklist["pneus"] = image

            send_message(phone, "Ligue os faróis e envie uma foto, por favor.")
            sessions[phone].action["type"] = "checklist-lanternas"
            return

        # Checklist lanternas
        if(sessions[phone].action["type"] == "checklist-lanternas"):
            if(text != "imagem"):
                send_message(phone, "Por favor, envie uma imagem.")
                return
            sessions[phone].checklist["farois"] = image
            send_message(phone, "Agora as lanternas traseiras, ligue as lanternas e envie uma foto, por favor.")
            sessions[phone].action["type"] = "checklist-limpeza"
            return

        # Checklist limpeza
        if(sessions[phone].action["type"] == "checklist-limpeza"):
            if(text != "imagem"):
                send_message(phone, "Por favor, envie uma imagem.")
                return
            sessions[phone].checklist["lanternas"] = image
            send_message(phone, "Falta somente conferirmos a limpeza interna, envie uma foto e finalizamos.")
            sessions[phone].action["type"] = "finalizacao-checklist"
            return

        # Finalizacao checklist
        if(sessions[phone].action["type"] == "finalizacao-checklist"):
            if(text != "imagem"):
                send_message(phone, "Por favor, envie uma imagem.")
                return
            sessions[phone].checklist["limpeza"] = image
            send_message(phone, "Lembre de conferir se não teve nenhum outro problema com o veículo e boa viagem!")
            api.send_checklist(phone, sessions[phone].checklist)
            sessions[phone].action["type"] = "next"
            return

    # Se a sessao ainda nao foi criada
    else:
        # Responde pelo nome
        if(info["name"] == "Amigo Caminhoneiro"):
            send_message(phone, "Olá! Como você ainda não possui cadastro, posso te chamar de *Amigo*?")
        else:
            send_message(phone, "Olá *" + name + '*!')
        # Envia opções do menu
        send_message(phone, menu_text)

        # cria sessao atrelada ao menu
        sessions[phone] = Session(phone, {"type": "menu"})

        print(info["name"] + ": Iniciou sessão.")
        return


def send_message(phone, message):
    url = "http://localhost:3000/send_message"

    payload = {
        "number": phone,
        "message": message
    }

    requests.request("POST", url, headers={'Content-Type': 'application/json'}, data=json.dumps(payload))


@app.route('/api/webhook', methods=['POST'])
def webhook():
    data = request.json
    data[0]["Body"]["Info"]["RemoteJid"] = data[0]["Body"]["Info"]["RemoteJid"].split('@')[0]
    if(len(data[0]["Body"]["Info"]["RemoteJid"]) == 12):
        data[0]["Body"]["Info"]["RemoteJid"] = data[0]["Body"]["Info"]["RemoteJid"][:4] + '9' + data[0]["Body"]["Info"]["RemoteJid"][4:]
    check_message(data)
    try:
        if(data[0]["image"]):
            pass
    except:
        pass
    return 'Dados Recebidos'


if __name__ == '__main__':
    app.config['SESSION_TYPE'] = 'filesystem'
    app.debug = True
    app.run(host='0.0.0.0')
    # app.run(host='192.168.100.90')
