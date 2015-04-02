import com.winbits.bits.Account
import com.winbits.bits.AccountStatus
import com.winbits.bits.AccountType
import com.winbits.bits.AccountValidity
import com.winbits.bits.OperationBitCommand
import com.winbits.bits.BagEnum
import com.winbits.bits.Bag
import com.winbits.bits.secure.Role
import com.winbits.bits.secure.User
import com.winbits.bits.secure.UserRole
import grails.util.Environment
import org.joda.time.DateTime

class BootStrap {

  def accountService

  def init = {servletContext ->
    if (Environment.current == Environment.PRODUCTION) {
      log.info('Creating winbits production user')
      createAdminUser('winbits', '.OE0(}W}|^iFCQ#')
    } else {
      createAdminUser('admin', 'admin')
    }
    createDefaultBags()
  }

  private createDefaultBags(){
    BagEnum.values().each {
      def bag = Bag.findByName(it.name)
      if( !bag ){
        def account = createNewAccount()
	new Bag(name: it.name, description: it.description, 
	            account: account).save()
      }
    }
  }

  private Account createNewAccount() {
    new Account(balance: 0, type: AccountType.PROMOTION, 
           status: AccountStatus.ACTIVE).save()
  }

  private User createAdminUser(String username, String password) {
    def adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')

    def adminUser = User.findByUsername(username)
    if (!adminUser) {
      adminUser = new User(username: username, password: password, enabled: true).save()
      UserRole.create(adminUser, adminRole)
    }
    adminUser
  }

  def destroy = {
  }
}
