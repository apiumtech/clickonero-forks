package com.winbits.orders.config

winbits {
  cybersource {
    merchantId = "clickonero"
    transactionKey = "pKXMRBNcLaX73LcToam21avKFFrAIx96uq6FXJ5uVn1qSaqred2/LKjo7ZWmm3/x6DFZGIWaat/kKK0/vckXRBeRKSyyC8fcZCNB0sC1hG2SQRvVipoIP9j5kAgfcNrDnCQkkcfu4ofrTRiF6hpeJwKXMmBape4lDlRTk5spZKjuy3B9+j5b/+LFpMSUHG2NUcoUWsAjH3q6roVcnm5WfWpJqqt53b8sqOjtlaabf/HoMVkYhZpq3+QorT+9yRdEF5EpLLILx9xkI0HSwLWEbZJBG9WKmgg/2PmQCB9w2sOcJCSRx+7ih+tNGIXqGl4nApcyYFql7iUOVFOTmylkqA=="
    transactionURL = "https://ics2wstest.ic3.com/commerce/1.x/transactionProcessor"
  }
  installment {
    cybersource {
      merchantId = "clickonero_installments"
      transactionKey = "tWEZ0OzgAWiWCJlX4l92hsQZqZ4n04x7sVLDbM44i9kj+CR/wCfJ/C+mJRsSXiSDZcO7FnqrJ+UIXbHwZsopQTf+NDWr8dstAXN+otsN4pxJZuRSZ36McwJBAMN+J8bsKFQ9Yb+ER9Lu+/Sgpxv2ILPNPboqjm9ik02BqwDHhDt6beOWer/bfnSY+aviX3aGxBmpnifTjHuxUsNszjiL2SP4JH/AJ8n8L6YlGxJeJINlw7sWeqsn5QhdsfBmyilBN/40Navx2y0Bc36i2w3inElm5FJnfoxzAkEAw34nxuwoVD1hv4RH0u779KCnG/Ygs809uiqOb2KTTYGrAMeEOw=="
      transactionURL = "https://ics2wstest.ic3.com/commerce/1.x/transactionProcessor"
    }

  }
}

environments {
  production {
    winbits.cybersource.merchantId = "clickonero_winbits"
    winbits.cybersource.transactionKey = "4bFKIDdCYTiPaXCrNSZ4GoVLlcLB/9QRZr+Ldh5VIu/GMX9Oet7emccwBiSW88+tK7bYgZEH9w0usGcxVCzfG3nKF1ZKe1z6gcxhUSpYSTmX6G4truCrRixHFfXYHUDnRiv6jLPizDwqNYnKoh7NJU4E+pSfFEZ+ocQCpOM5jiwy0+TmAzQmIhVKNjIp3rYOTOkrxkr0y5WJ/hfy1QU73zi+4xI5YgQpm/flffxjTI1lGspiX9OTUVDoPa+o5H+zfpbBPUY9wyZqfa6q38EqdEPEkj7Epu65uz6/JkgJbrY8iG8uNAynV+3GFL32BoDRy/iMlX4csKJj3dwKO9YVkA=="
    winbits.cybersource.transactionURL = "https://ics2ws.ic3.com/commerce/1.x/transactionProcessor/"

    winbits.installment.cybersource.merchantId = "clickonero_winbits_inst"
    winbits.installment.cybersource.transactionKey = "vOppyYsxAadc5iOqDiNQDFRgsaN+mihqpQieb2QWW0cOyiHkl3KgHb4K2UgXxLCVbSsq4/Dbl2oQaVLOsBdYnTNNBwq5VW+svU1aob/HKg5gJs8y0eiB+PnC9TrFqMm7C583Zhbi/7ML8eIxxzHH35ckbF8FkPLky7Z08NnW4cnMw6u/douSD6rY6kJ0xB9zNf9lpLIfytsxKrIVpRrC8YG+npMOZa6uE3FEp+iF7IModb6ChxdzET6IobxVEwQMDHcLwkunvKEpYctXVBV7f9csoMp+x6IeS6Mj5aTZbzjKCw587Nxb2OfrPHjpF7M5E81f8pzmhprm8QlEVRujyg=="
    winbits.installment.cybersource.transactionURL = "https://ics2ws.ic3.com/commerce/1.x/transactionProcessor/"
  }
}


