# AutoAttack Mod — Fabric 1.20.1

Automatycznie atakuje moba/mob gdy najeżdżasz na niego celownikiem.

## Sterowanie
- **R** — włącz/wyłącz autoatak (toggle)
- Komunikat pojawia się w action barze (nad hotbarem)

## Działanie
- Wykrywa encję pod celownikiem co tick
- Atakuje co ~0.5 sekundy (10 ticków) — zgodnie z cooldownem broni
- Atakuje wszelkie mobs (zombie, skeleton, creeper, zwierzęta itp.)
- **NIE atakuje innych graczy** — bezpieczny na serwerach

## Jak zbudować (wymaga Java 17+)

```bash
# 1. Pobierz Gradle Wrapper
gradle wrapper --gradle-version 8.4

# 2. Zbuduj moda
./gradlew build

# 3. Plik .jar znajdziesz w:
build/libs/autoattack-mod-1.0.0.jar
```

## Instalacja
1. Zainstaluj [Fabric Loader](https://fabricmc.net/use/installer/) dla Minecraft 1.20.1
2. Zainstaluj [Fabric API](https://modrinth.com/mod/fabric-api)
3. Wrzuć `autoattack-mod-1.0.0.jar` do folderu `.minecraft/mods/`

## Zmiana klawisza
Wejdź w Opcje → Sterowanie → Skróty klawiszowe → "AutoAttack Mod"

## Zmiana interwału ataku
W pliku `AutoAttackMod.java` zmień wartość:
```java
private static final int ATTACK_INTERVAL = 10; // 10 ticków = 0.5s
// np. 20 = 1 sekunda, 5 = 0.25 sekundy
```
