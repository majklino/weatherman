# Weatherman
Spring Boot webová aplikace pro měření, zobrazování a průběžné ukládání reálných teplot pro libovolné reálné město. Aplikace umožňuje provádět měření manuálně na pokyn uživatele, ale i automaticky v určitém časovém intervalu. Uživatel může přidat nové město, provést pro něj měření nebo odebrat dříve přidané město.
## User interface
Aplikace poskytuje user interface ve formě single page responzivní stránky, pomocí které lze ovládat většinu vlastností
## Rest Api
Aplikace také nabízí REST Api, které lze použít k dosažení stejných výsledků jako user interface + další možnosti jako např. csv export, seznam všech měst atd.
## CSV Export
Pomocí HTTP requestu, endpoint = "\api\csv".
