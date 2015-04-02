package com.winbits.controllers.command

/**
 * Created with IntelliJ IDEA.
 * User: death
 * Date: 15/05/14
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
class PaginateCommand {
    Integer max
    Integer offset
    String sort
    String order

    Map params() {
        [max: max, offset: offset, sort: sort, order: order]
    }
}
