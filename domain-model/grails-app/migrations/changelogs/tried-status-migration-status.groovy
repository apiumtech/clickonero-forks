package changelogs
/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 27/02/15
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */


databaseChangeLog = {

    changeSet(author: "winbits", id: "verticalpartner-01") {
        sql("INSERT INTO migration_status (id,version, description, name)" +
                "VALUES (3, 0,null, 'TRIED');")
    }

}
