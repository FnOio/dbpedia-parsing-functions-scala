import functions.SimplePropertyFunction;

/**
 * Created by wmaroy on 15.11.16.
 */
public class Main {
    public static void main(String[] args) {

        String test = "|conventional_long_name = Kingdom of Belgium\n" +
                "|native_name = {{vunblist|item_style=font-size:88%; |{{native name|nl|Koninkrijk België}} |{{native name|fr|Royaume de Belgique}} |{{native name|de|Königreich Belgien}}}}\n" +
                "|common_name = Belgium\n" +
                "|image_flag = Flag of Belgium.svg <!--Please do not replace the official 13:15 size flag by the more commonly used civil flag. Thank you.-->\n" +
                "|flag_caption = Flag\n" +
                "|image_coat = Great Coat of Arms of Belgium.svg\n" +
                "|symbol_type = Coat of arms\n" +
                "|national_motto = {{native phrase|nl|\"Eendracht maakt macht\"|italics=off}}<br />{{native phrase|fr|\"L'union fait la force\"|italics=off}}<br />{{native phrase|de|\"Einigkeit macht stark\"|italics=off}}\n" +
                "|englishmotto = \"Unity makes Strength\"\n" +
                "|national_anthem = \"[[Brabançonne]]\"<div style=\"padding-top:0.2em;height:10px;\"><center>[[File:The Brabanconne.ogg]]</center></div>{{small|(instrumental version)}}\n" +
                "|image_map = EU-Belgium.svg\n" +
                "|image_map2 = Belgium - Location Map (2013) - BEL - UNOCHA.svg\n" +
                "|map_caption = {{map caption |country={{nobold|Belgium}}|location_color=dark green |region=Europe |region_color=dark grey |subregion=the European Union |subregion_color=green}}\n" +
                "|official_languages = [[Dutch language|Dutch]]<br>[[French language|French]]<br>[[German language|German]]\n" +
                "|demonym = Belgian\n" +
                "|ethnic_groups = see ''[[#Demographics|Demographics]]''\n" +
                "|capital = [[City of Brussels|Brussels]]\n" +
                "|latd=50 |latm=51 |latNS=N |longd=4 |longm=21 |longEW=E\n" +
                "|largest_city = [[Antwerp]] and [[Charleroi]]\n" +
                "|government_type = [[Federalism|Federal]] [[Parliamentary system|parliamentary]]<br>[[constitutional monarchy]]<ref>{{cite web|title=Government type: Belgium|url=https://www.cia.gov/library/publications/the-world-factbook/fields/2128.html|work=The World Factbook|publisher=CIA|accessdate=19 December 2011}}</ref>\n" +
                "|leader_title1 = [[Monarchy of Belgium|Monarch]]\n" +
                "|leader_name1 = [[Philippe of Belgium|Philippe]]\n" +
                "|leader_title2 = [[Prime Minister of Belgium|Prime Minister]]\n" +
                "|leader_name2 = [[Charles Michel]]\n" +
                "|legislature = [[Belgian Federal Parliament|Federal Parliament]]\n" +
                "|upper_house = [[Senate (Belgium)|Senate]]\n" +
                "|lower_house = [[Chamber of Representatives (Belgium)|Chamber of Representatives]]\n" +
                "|area_km2 = 30,528\n" +
                "|area_sq_mi = 11,787 <!--Do not remove per [[WP:MOSNUM]]-->\n" +
                "|area_rank = 140th\n" +
                "|percent_water = 6.4\n" +
                "|population_census = 11,250,585<ref name=Population>{{cite web|url=http://www.ibz.rrn.fgov.be/fileadmin/user_upload/fr/pop/statistiques/population-bevolking-20160101.pdf|title=Bevolkingscijfers per provincie en per gemeente op 1 januari 2016/Chiffres de la population par province et par commune, a la date du 1er Janvier 2016|publisher=[[Statistics Belgium]], [[Federal Public Service Economy]]|date=24 January 2016|accessdate=24 March 2016|format=PDF}}</ref><!-- Belgium does not work with censuses and estimates but has an always up-to-date population register, with official data for 1 January of each year. Monthly updated statistics are available via http://www.ibz.rrn.fgov.be/fileadmin/user_upload/Registre/nl/statistieken_bevolking/stat_1_n.pdf -->\n" +
                "|population_census_year = 1 January 2016\n" +
                "|population_census_rank = 75th\n" +
                "|population_density_km2 = 363.6<!-- based on 2013-01-01 population number -->\n" +
                "|population_density_rank = 23rd\n" +
                "|population_density_sq_mi = 941.68 <!--Do not remove [[WP:MOSNUM]] / based on 2013-01-01 population number -->\n" +
                "|GDP_PPP_year = 2015\n" +
                "|GDP_PPP = $494.620 billion<ref name=IMF>{{cite web|url=http://www.imf.org/external/pubs/ft/weo/2015/02/weodata/weorept.aspx?pr.x=58&pr.y=2&sy=2015&ey=2020&scsm=1&ssd=1&sort=country&ds=.&br=1&c=124&s=NGDPD%2CNGDPDPC%2CPPPGDP%2CPPPPC&grp=0&a=|title=Belgium|publisher=International Monetary Fund|accessdate=October 2015}}</ref>\n" +
                "|GDP_PPP_rank = 38th\n" +
                "|GDP_PPP_per_capita = $43,629<ref name=IMF/>\n" +
                "|GDP_PPP_per_capita_rank = 20th\n" +
                "|GDP_nominal_year = 2015\n" +
                "|GDP_nominal = $458.651 billion<ref name=IMF/>\n" +
                "|GDP_nominal_rank = 23rd\n" +
                "|GDP_nominal_per_capita = $40,456<ref name=IMF/>\n" +
                "|GDP_nominal_per_capita_rank = 17th\n" +
                "|Gini_year = 2011\n" +
                "|Gini_change = <!--increase/decrease/steady-->\n" +
                "|Gini = 26.3 <!--number only-->\n" +
                "|Gini_ref = <ref name=eurogini>{{cite web|title=Gini coefficient of equivalised disposable income (source: SILC)|url=http://appsso.eurostat.ec.europa.eu/nui/show.do?dataset=ilc_di12|publisher=Eurostat Data Explorer|accessdate=13 August 2013}}</ref>\n" +
                "|HDI_year = 2014<!-- Please use the year to which the data refers, not the publication year-->\n" +
                "|HDI_change = increase<!--increase/decrease/steady-->\n" +
                "|HDI = 0.890 <!--number only-->\n" +
                "|HDI_ref = <ref>{{cite web|url=http://hdr.undp.org/sites/default/files/hdr_2015_statistical_annex.pdf|title=Human Development Report 2015|publisher=United Nations|format=PDF|accessdate=14 December 2015}}</ref>\n" +
                "|HDI_rank = 21st\n" +
                "|sovereignty_type = [[Belgian Revolution|Independence]]\n" +
                "|sovereignty_note = from the [[United Kingdom of the Netherlands|Netherlands]]\n" +
                "|established_event1 = Declared\n" +
                "|established_date1 = 4 October 1830\n" +
                "|established_event2 = [[Treaty of London, 1839|Recognised]]\n" +
                "|established_date2 = 19 April 1839\n" +
                "|established_event3 = [[Enlargement of the European Union#Founding members|Founded]] the [[European Economic Community|EEC]] (now the [[European Union|EU]])\n" +
                "|established_date3 = 1 January 1958\n" +
                "|currency = [[Euro]] ([[Euro sign|€]])\n" +
                "|currency_code = EUR\n" +
                "|time_zone = [[Central European Time|CET]]\n" +
                "|utc_offset = +1\n" +
                "|time_zone_DST = [[Central European Summer Time|CEST]]\n" +
                "|utc_offset_DST = +2\n" +
                "|drives_on = right\n" +
                "|calling_code = [[Telephone numbers in Belgium|+32]]\n" +
                "|cctld = [[.be]]\n" +
                "|footnote_a = The flag's official proportions of 13:15 are rarely seen; proportions of 2:3 or similar are more common.\n" +
                "|footnote_b = The [[Brussels]] region is the ''de facto'' capital, but the [[City of Brussels]] municipality is the ''de jure'' capital<ref>{{cite book|title=The Belgian Constitution|date=May 2014|publisher=Belgian House of Representatives|location=Brussels, Belgium|page=63|url=http://www.const-court.be/en/basic_text/belgian_constitution.pdf|format=PDF|accessdate=10 September 2015}}</ref>\n" +
                "|footnote_c = The [[.eu]] domain is also used, as it is shared with other European Union member states.\n";

        String[] properties = test.split("\n");
        for(int i = 0; i < properties.length; i++) {
            properties[i] = properties[i].substring(1);
            try {
                SimplePropertyFunction fn = new SimplePropertyFunction(properties[i], null, null, null, "external", 1.0, "PopulationfghDensity", "inhabitantsPerSquareKilometre");
                String[] list = fn.execute();
                for (int j = 0; j < list.length; j++) {
                    System.out.println(list[j]);
                }
            } catch (Exception e) {
                System.err.println("\n\nSkipping: " + properties[i].split("=")[0]);
                System.err.println(properties[i].split("=")[1]);
                System.err.println("--------------------------------------------------");
                e.printStackTrace();
                System.err.println("");
            }
        }

        System.out.println("Done");

    }

}
