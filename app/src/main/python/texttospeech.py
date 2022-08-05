from googletrans import Translator

def texttospeech(languageCode,data):
   translator =  Translator()
   translated_text = translator.translate(data, dest =languageCode)

   return translated_text.text

