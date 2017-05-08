package dbpedia.dataparsers.util.wikiparser.impl.wikipedia

import dbpedia.dataparsers.util.Language
import dbpedia.dataparsers.util.RichString.wrapString

/**
 * Holds the redirect identifiers of the different Wikipedia languages.
 * Generated by Generate.php.
 * 
 * TODO: get them complete and up-to-date from 
 * http://af.wikipedia.org/wiki/MediaWiki:Disambiguationspage
 * http://am.wikipedia.org/wiki/MediaWiki:Disambiguationspage
 * http://ar.wikipedia.org/wiki/MediaWiki:Disambiguationspage
 * and so on...
 */
object CuratedDisambiguation
{
    private val map = Map(
      "ab" -> Set("Неоднозначность"),
      "ace" -> Set("Disambig"),
      "af" -> Set("Dubbelsinnig"),
      "aln" -> Set("Kthjellim"),
      "als" -> Set("Begriffsklärig"),
      "am" -> Set("መንታ"),
      "an" -> Set("Desambigazión"),
      "ar" -> Set("توضيح"),
      "arn" -> Set("Desambiguación"),
      "arz" -> Set("توضيح"),
      "as" -> Set("দ্ব্যর্থতা_নিরসন"),
      "ast" -> Set("dixebra"),
      "av" -> Set("Неоднозначность"),
      "avk" -> Set("Milyoltaca"),
      "ay" -> Set("Desambiguación"),
      "az" -> Set("dəqiqləşdirmə"),
      "ba" -> Set("Неоднозначность"),
      "bar" -> Set("Begriffsklärung"),
      "bat-smg" -> Set("Daugiareikšmis"),
      "bcc" -> Set("رفع ابهام"),
      "bcl" -> Set("clarip"),
      "be" -> Set("Неадназначнасць"),
      "be-tarask" -> Set("Неадназначнасьць"),
      "be-x-old" -> Set("Неадназначнасьць"),
      "bg" -> Set("Пояснение"),
      "bm" -> Set("Homonymie"),
      "bn" -> Set("দ্ব্যর্থতা_নিরসন"),
      "bpy" -> Set("দ্ব্যর্থতা_নিরসন"),
      "bqi" -> Set("ابهام‌زدایی"),
      "br" -> Set("Project:Liammoù_ouzh_ar_pajennoù_disheñvelaat"),
      "bs" -> Set("Čvor"),
      "bug" -> Set("Disambig"),
      "ca" -> Set("Desambiguació"),
      "cbk-zam" -> Set("Desambiguación"),
      "ce" -> Set("Неоднозначность"),
      "ch" -> Set("disambig"),
      "crh" -> Set("disambig"),
      "crh-cyrl" -> Set("disambig"),
      "crh-latn" -> Set("disambig"),
      "cs" -> Set("Rozcestník"),
      "csb" -> Set("Starnë_ùjednoznacznieniô"),
      "cu" -> Set("мъногосъмꙑ́слиѥ"),
      "cv" -> Set("Disambig"),
      "cy" -> Set("Gwahaniaethu"),
      "da" -> Set("Flertydig"),
      "de" -> Set("Begriffsklärung"),
      "de-at" -> Set("Begriffsklärung"),
      "de-ch" -> Set("Begriffsklärung"),
      "de-formal" -> Set("Begriffsklärung"),
      "dk" -> Set("Flertydig"),
      "dsb" -> Set("Rozjasnjenje zapśimjeśow"),
      "el" -> Set("αποσαφήνιση"),
      "eml" -> Set("Disambigua"),
      "en" -> Set("Disambig", "Dab", "Disambiguation", "Disambig-Chinese-char-title", "Disambig-cleanup", "Fish-dab", "Geodis", "hndis", "Hndis", "hndis-cleanup", "Hndis-cleanup", "Hospitaldis", "Letter disambig", "Mathdab", "NA Broadcast List", "Numberdis", "POWdis", "Roaddis", "Schooldis", "SIA", "Shipindex", "Schooldis", "Mountainindex", "Given name", "Surname"),
      "eo" -> Set("Apartigilo"),
      "es" -> Set("Desambiguación"),
      "eu" -> Set("argipen"),
      "ext" -> Set("desambiguáncia"),
      "fa" -> Set("ابهام‌زدایی"),
      "ff" -> Set("Homonymie"),
      "fi" -> Set("Täsmennyssivu"),
      "fiu-vro" -> Set("Linke täpsüstüslehekülile"),
      "fo" -> Set("fleiri týdningar"),
      "fr" -> Set("Homonymie"),
      "frp" -> Set("Homonimia"),
      "fur" -> Set("Disambigua"),
      "fy" -> Set("Neibetsjuttings"),
      "ga" -> Set("{{ns:project}}:Naisc_go_leathanaigh_idirdhealaithe"),
      "gag" -> Set("Anlam ayrımı"),
      "gan" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "gan-hans" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "gan-hant" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "gl" -> Set("Homónimos"),
      "glk" -> Set("ابهام‌زدایی"),
      "gn" -> Set("Disambig"),
      "grc" -> Set("σαφήνισις"),
      "gsw" -> Set("Begriffsklärig"),
      "he" -> Set("פירושונים"),
      "hi" -> Set("disambig"),
      "hif" -> Set("disambig"),
      "hif-latn" -> Set("disambig"),
      "hr" -> Set("Razdvojba"),
      "hsb" -> Set("Wjacezmyslnosć"),
      "ht" -> Set("Homonymie"),
      "hu" -> Set("Egyért"),
      "hy" -> Set("Երկիմաստ"),
      "ia" -> Set("Disambiguation"),
      "id" -> Set("Disambig"),
      "ii" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "ilo" -> Set("disambig"),
      "inh" -> Set("Неоднозначность"),
      "is" -> Set("Aðgreining"),
      "it" -> Set("Disambigua"),
      "ja" -> Set("Aimai"),
      "jut" -> Set("Flertydig"),
      "jv" -> Set("Disambig"),
      "ka" -> Set("მრავალმნიშვნელოვანი"),
      "kaa" -> Set("disambig"),
      "kab" -> Set("Asefham"),
      "kk" -> Set("Айрық"),
      "kk-arab" -> Set("ايرىق"),
      "kk-cn" -> Set("ايرىق"),
      "kk-cyrl" -> Set("Айрық"),
      "kk-kz" -> Set("Айрық"),
      "kk-latn" -> Set("Aýrıq"),
      "kk-tr" -> Set("Aýrıq"),
      "kl" -> Set("Flertydig"),
      "kn" -> Set("ದ್ವಂದ್ವ ನಿವಾರಣೆ"),
      "ko" -> Set("Disambig"),
      "ksh" -> Set("Disambig"),
      "ku" -> Set("disambig"),
      "ku-arab" -> Set("disambig"),
      "ku-latn" -> Set("disambig"),
      "kv" -> Set("Неоднозначность"),
      "la" -> Set("Discretiva"),
      "lad" -> Set("Desambiguación"),
      "lb" -> Set("Homonymie"),
      "lbe" -> Set("Неоднозначность"),
      "li" -> Set("Verdudeliking"),
      "lij" -> Set("Disambigua"),
      "lld" -> Set("Disambigua"),
      "lmo" -> Set("Disambigua"),
      "ln" -> Set("Homonymie"),
      "lt" -> Set("Daugiareikšmis"),
      "lv" -> Set("Disambig"),
      "mai" -> Set("disambig"),
      "map-bms" -> Set("Disambig"),
      "mdf" -> Set("лама смусть"),
      "mg" -> Set("Homonymie"),
      "mhr" -> Set("Неоднозначность"),
      "mk" -> Set("Појаснување"),
      "ml" -> Set("നാനാര്‍ത്ഥം"),
      "mn" -> Set("Салаа утгатай"),
      "mo" -> Set("Dezambiguizare"),
      "mr" -> Set("नि:संदिग्धीकरण"),
      "ms" -> Set("disambig"),
      "mt" -> Set("diżambig"),
      "mwl" -> Set("disambig"),
      "myv" -> Set("смустень коряс явома"),
      "mzn" -> Set("ابهام‌زدایی"),
      "nah" -> Set("Desambiguación"),
      "nan" -> Set("disambig", "KhPI", "Khu-pia̍t-iah", "Khu-pia̍t-ia̍h"),
      "nap" -> Set("Disambigua"),
      "nb" -> Set("Peker"),
      "nds" -> Set("Mehrdüdig_Begreep"),
      "nds-nl" -> Set("Dv"),
      "nl" -> Set("Dp", "Dpintro"),
      "nn" -> Set("Fleirtyding"),
      "no" -> Set("Peker"),
      "oc" -> Set("Omonimia"),
      "os" -> Set("Неоднозначность"),
      "pam" -> Set("pamipalino"),
      "pdc" -> Set("Begriffsklärung"),
      "pl" -> Set("disambig"),
      "pms" -> Set("Gestion dij sinònim"),
      "pt" -> Set("Disambig","Desambiguação","Disambiguation"),
      "pt-br" -> Set("Disambig","Desambiguação","Disambiguation"),
      "qu" -> Set("Sut\'ichana qillqa"),
      "rmy" -> Set("Dezambiguizare"),
      "ro" -> Set("Dezambiguizare"),
      "roa-tara" -> Set("disambigue"),
      "ru" -> Set("Неоднозначность"),
      "ruq" -> Set("Dezambiguizare"),
      "ruq-cyrl" -> Set("Појаснување"),
      "ruq-grek" -> Set("Project:Σύνδεσμοι_προς_τις_σελίδες_αποσαφήνισης"),
      "ruq-latn" -> Set("Dezambiguizare"),
      "sa" -> Set("disambig"),
      "sah" -> Set("элбэх суолталаах өйдөбүллэр"),
      "sc" -> Set("Disambigua"),
      "scn" -> Set("Disambigua"),
      "sdc" -> Set("Matessi innòmmu"),
      "shi" -> Set("توضيح"),
      "si" -> Set("වක්‍රෝත්තිහරණ"),
      "simple" -> Set("disambig"),
      "sk" -> Set("Rozlišovacia stránka"),
      "sl" -> Set("Razločitev"),
      "sq" -> Set("Kthjellim"),
      "sr" -> Set("Вишезначна одредница"),
      "sr-ec" -> Set("Вишезначна одредница"),
      "sr-el" -> Set("Višeznačna odrednica"),
      "srn" -> Set("Doorverwijspagina"),
      "stq" -> Set("Begriepskläärenge"),
      "su" -> Set("Project:Tumbu_ka_kaca_disambiguasi"),
      "sv" -> Set("Förgrening"),
      "szl" -> Set("disambig"),
      "ta" -> Set("பக்கவழி நெறிப்படுத்தல்"),
      "tcy" -> Set("ದ್ವಂದ್ವ ನಿವಾರಣೆ"),
      "te" -> Set("అయోమయ నివృత్తి"),
      "tg" -> Set("ибҳомзудоӣ"),
      "tg-cyrl" -> Set("ибҳомзудоӣ"),
      "th" -> Set("แก้กำกวม"),
      "tl" -> Set("disambig"),
      "to" -> Set("Fakaʻuhingakehe"),
      "tr" -> Set("Anlam ayrımı"),
      "tt-cyrl" -> Set("Неоднозначность"),
      "ty" -> Set("Homonymie"),
      "udm" -> Set("Неоднозначность"),
      "uk" -> Set("disambig"),
      "uz" -> Set("Disambig"),
      "vec" -> Set("Disambigua"),
      "vep" -> Set("Äiznamoičenduz"),
      "vi" -> Set("disambig"),
      "vls" -> Set("Doorverwijspagina"),
      "vo" -> Set("Telplänov"),
      "vro" -> Set("Linke täpsüstüslehekülile"),
      "wa" -> Set("Omonimeye"),
      "wo" -> Set("Homonymie"),
      "wuu" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "xmf" -> Set("მრავალმნიშვნელოვანი"),
      "ydd" -> Set("באדייטן"),
      "yi" -> Set("באדייטן"),
      "yue" -> Set("disambig", "搞清楚"),
      "za" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zea" -> Set("Doorverwijspagina"),
      "zh" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zh-cn" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zh-hans" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zh-hant" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zh-hk" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zh-min-nan" -> Set("disambig", "KhPI", "Khu-pia̍t-iah", "Khu-pia̍t-ia̍h"),
      "zh-mo" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zh-my" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zh-sg" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zh-tw" -> Set("disambig", "消含糊", "消除含糊", "消歧义", "消除歧义", "消歧義", "消除歧義"),
      "zh-yue" -> Set("disambig", "搞清楚"))

    // TODO: capitalize the values in the source code, not each time they are requested
    def get(language : Language) : Option[Set[String]] = map.get(language.wikiCode).map(_.map(_.capitalize(language.locale)))
}
