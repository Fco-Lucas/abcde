// export function getSubtleCrypto(): SubtleCrypto {
//   if (typeof window !== 'undefined' && window.crypto && window.crypto.subtle) {
//     return window.crypto.subtle;
//   }
//   throw new Error('Web Crypto API não disponível');
// }

// export async function calculateHash(file: File): Promise<string> {
//   const buffer = await file.arrayBuffer();
//   const subtle = getSubtleCrypto();
//   const hashBuffer = await subtle.digest('SHA-256', buffer);
//   const hashArray = Array.from(new Uint8Array(hashBuffer));
//   return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
// }

import * as CryptoJS from 'crypto-js';

export function calculateHash(file: File): Promise<string> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => {
      const wordArray = CryptoJS.lib.WordArray.create(reader.result as ArrayBuffer);
      const hash = CryptoJS.SHA256(wordArray).toString(CryptoJS.enc.Hex);
      resolve(hash);
    };
    reader.onerror = reject;
    reader.readAsArrayBuffer(file);
  });
}
