# RSA Keys generation

## create rsa key pair
```bash
openssl genrsa -out keypair.pem 2048
```
## extract public key
```bash
openssl rsa -in keypair.pem -pubout -out public.pem
```

## create private key in PKCS#8 format
```bash
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```