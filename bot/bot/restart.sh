echo -e "\n\e[94mEncerrando aplicação:\e[37m"

kill -9 $(ps aux | grep '[n]odejs ./whatsapp-bot-hack.js' | awk '{print $2}') >/dev/null 2>&1
echo -e "Encerrada\n"

echo -e "\e[94mRemovendo sessão anterior\e[37m\n\n"
rm -R session-bot

echo -e "\e[94mExecutando aplicação atualizada:\e[37m\n\n"
npm start &> ./log.txt &

echo "Executando."
