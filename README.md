## 🚀 Konfiguracja projektu

Aby uruchomić aplikację, potrzebujesz własnego klucza API do modelu Gemini.

1.  **Zdobądź klucz API**:
    *   Wejdź na stronę [Google AI Studio](https://aistudio.google.com/app/apikey).
    *   Zaloguj się i kliknij przycisk **"Get API Key"**.
    *   Wygeneruj nowy klucz i skopiuj go.
        > Ewentualnie użyj publicznego klucza = "AIzaSyCakjfTUBy3wHlC#####DYv##.#zvuIaG#.##.KiHMuEP4mk" (bez hashtagów i kropek oczywiście. Google inaczej nie pozwala mi tego wkleić)

2.  **Skonfiguruj lokalny plik z kluczem**:
    *   W głównym katalogu projektu (tam, gdzie znajduje się plik `local.properties.example`) utwórz kopię tego pliku i nazwij ją `local.properties`.
    *   Otwórz nowo utworzony plik `local.properties`.
    *   W linii `GEMINI_API_KEY=TU_WKLEJ_SWOJ_KLUCZ` wklej swój prawdziwy klucz API.
        > Pamiętaj, aby nie używać cudzysłowów.
    *   Po tych zmianach zsynchronizuj projekt z plikami Gradle (przycisk "Sync Now" w Android Studio).

3.  To wszystko! Możesz teraz zbudować i uruchomić aplikację.
