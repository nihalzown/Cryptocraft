from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
from Crypto.Hash import sha256
import base64

def encrypt_aes(plaintext: str, key: str) -> str:
    try:
        plaintext_bytes = plaintext.encode('utf-8')
        key_bytes = key.encode('utf-8')
        hasher = sha256.new(key_bytes)
        aes_key = hasher.digest()[:16]
        cipher = AES.new(aes_key, AES.MODE_CBC)
        ciphertext = cipher.encrypt(pad(plaintext_bytes, AES.block_size))
        iv = cipher.iv
        encrypted_package = base64.b64encode(iv + ciphertext)
        return encrypted_package.decode('utf-8')
    except Exception as e:
        print(f"Encryption failed: {e}")
        return None
    


if __name__ == "__main__":
    print("Testing encryption service...")
    myplaintext = "i am a secret message"
    mysecretkey = "my_secret_key"
    encrypted = encrypt_aes(myplaintext, mysecretkey)
    if encrypted:
        print(f"Encrypted message: {encrypted}")
    else:
        print("Encryption failed.")