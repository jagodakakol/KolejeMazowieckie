# Koleje Mazowieckie 🚆

Aplikacja multiplatformowa (Kotlin Multiplatform + Compose Multiplatform) prezentująca tabor
Kolei Mazowieckich — elektryczne zespoły trakcyjne, lokomotywy i wagony piętrowe.
Użytkownik może przeglądać pojazdy, otwierać linki z informacjami oraz odhaczać te,
które już widział / którymi jechał.

Projekt zaliczeniowy z przedmiotu **Technologie internetowe**. Autor: **Jagoda Kąkol**.

## ✨ Funkcje
- Lista taboru KM ze zdjęciami (EN57AKM, EN71, EN76 Elf, ER75 FLIRT, ER160 FLIRT3, 45WE, EU47 Hetman, wagony piętrowe Twindexx).
- Przycisk **Info** otwierający stronę z informacjami o pojeździe.
- Odhaczanie pojazdów (checkbox).
- Działa na **Androidzie, Desktopie i w przeglądarce** (wspólny kod UI).
- Dane pobierane z własnego **serwera HTTP** połączonego z **bazą danych**.

## 🧱 Architektura
Aplikacja kliencka (`shared`): Clean Architecture + MVVM — warstwy `domain` (model, repozytorium, use case'y), `data` (źródła danych, repozytoria), `presentation` (ViewModel, widoki Compose).

Serwer (`server`): warstwy `domain` (model, interfejs repozytorium), `infrastructure` (baza danych Exposed, implementacja repozytorium), `application` (DTO, routes) oraz `Server.kt` (start, pluginy, obsługa błędów, Dependency Injection).

## 🛠️ Technologie
Compose Multiplatform, Material 3, MVVM, Dependency Injection, Ktor (Client + Server/Netty), kotlinx.serialization, StatusPages, Exposed, baza H2, Kotlin Multiplatform.

## ▶️ Uruchomienie
**Serwer** (musi działać, by aplikacja pobrała dane):
```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
.\gradlew :server:run
```
Serwer: http://localhost:8080 (test: http://localhost:8080/trains).

**Aplikacja:**
- Desktop: uruchom `main()` w `desktopApp/.../main.kt`.
- Web: konfiguracja `webApp [wasmJs]`.
- Android: moduł `androidApp` na emulatorze/urządzeniu.

## 🌐 API (REST)
| Metoda | Ścieżka | Opis | Kody |
|--------|---------|------|------|
| GET | `/trains` | Lista pojazdów | 200 |
| GET | `/trains/{id}` | Szczegóły pojazdu | 200 / 404 |
| POST | `/trains` | Dodanie pojazdu | 201 / 400 / 422 |
| PATCH | `/trains/{id}` | Aktualizacja | 204 / 404 |
| DELETE | `/trains/{id}` | Usunięcie | 200 / 404 |

Obsługa błędów (StatusPages): 400 (zły JSON), 422 (walidacja), 404 (brak zasobu), 500 (błąd serwera).

## 🧪 Testowanie
API testowane w **Postmanie** (wszystkie metody + przypadki błędów).