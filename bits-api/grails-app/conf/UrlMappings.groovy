class UrlMappings {

  static mappings = {
    "/"(view: "/index")

    "/accounts"(controller: "accounts", parseRequest: true) {
      action = [POST: "save"]
    }
    "/accounts/$id/balance"(controller: "accounts", parseRequest: true) {
      action = [GET: "balance"]
    }
    "/accounts/deposit"(controller: "accounts", parseRequest: true) {
      action = [POST: "deposit"]
    }
    "/accounts/$id/withdraw"(controller: "accounts", parseRequest: true) {
      action = [POST: "withdraw"]
    }
    "/accounts/transfer"(controller: "accounts", parseRequest: true) {
      action = [POST: "transfer"]
    }
    "/accounts/$id/history"(controller: "accounts", parseRequest: true) {
      action = [GET: "history"]
    }
    "/accounts/$id/rollback/"(controller: "accounts", parseRequest: true) {
      action = [POST: "rollback"]
    }
    "/bag"(controller: "bag", parseRequest: true) {
      action = [POST: "save"]
    }
    "/reward/register"(controller: "accounts", parseRequest: true) {
      action = [POST: "rewardRegister"]
    }
    "/find/reward/register"(controller: "accounts", parseRequest: true) {
      action = [GET: "findRewardRegister"]
    }
    "/reward/gift"(controller: "accounts", parseRequest: true) {
      action = [POST: "giftRegister"]
    }
  }
}
