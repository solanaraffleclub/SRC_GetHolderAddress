# SRC_GetHolderAddress

#Get NFT Tokens
Get NFT tokens using The Block Chain API - Search NFT (https://docs.blockchainapi.com/)

Request Type : POST
Request URL : https://api.blockchainapi.com/v1/solana/nft/search
Request Headers:
    APIKeyID : 
    APISecretKey : 
JSON Body : 
```json
    {
        "name": "Aurorian ",
        "name_search_method": "begins_with",
        "symbol": "AUROR",
        "symbol_search_method": "exact_match",
        "network": "mainnet-beta"
    }
```

Save Result as JSON Format

#Get NFT Owner
add "response.json" inside SRC_GetHolderAddress Folder.

Run Eclipse Main.java and see results under NFT_Addressess.csv