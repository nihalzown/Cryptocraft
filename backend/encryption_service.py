from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
from Crypto.Hash import SHA256
import base64


# modern ciphers

def encrypt_aes(plaintext: str, key: str) -> str:
    try:
        plaintext_bytes = plaintext.encode('utf-8')
        key_bytes = key.encode('utf-8')
        hasher = SHA256.new(key_bytes)
        aes_key = hasher.digest()[:16]
        cipher = AES.new(aes_key, AES.MODE_CBC)
        ciphertext = cipher.encrypt(pad(plaintext_bytes, AES.block_size))
        iv = cipher.iv
        encrypted_package = base64.b64encode(iv + ciphertext)
        return encrypted_package.decode('utf-8')
    except Exception as e:
        print(f"Encryption failed: {e}")
        return None
    
#classic ciphers

def encrypt_caesar(plaintext: str, shift: int) -> str:
    encrypted =""
    for char in plaintext:
        if char.isupper():
            encrypted += chr((ord(char) - ord('A') + shift) % 26 + ord('A'))
        elif char.islower():
            encrypted += chr((ord(char) - ord('a') + shift) % 26 + ord('a'))
        else:
            encrypted += char
    return encrypted


def encrypt_substitution(plaintext: str, key: str) -> str:
    alphabet = 'abcdefghijklmnopqrstuvwxyz'
    if len(key) != 26 or not key.isalpha() or len(set(key.lower())) != 26:
        return "Invalid substitution key"
    
    key_map = str.maketrans(alphabet + alphabet.upper(), key.lower() + key.upper())
    return plaintext.translate(key_map)







if __name__ == "__main__":
    print("Testing encryption service...")
    myplaintext = "i am a secret message"
    mysecretkey = "my_secret_key"
    encrypted = encrypt_aes(myplaintext, mysecretkey)
    print(f"Secret key used: {mysecretkey}")
    if encrypted:
        print(f"Encrypted message: {encrypted}")
    else:
        print("Encryption failed.")