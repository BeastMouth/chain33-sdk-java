package cn.chain33.javasdk.model.enums;

public enum RpcMethod {
		
	QUERY_TRANSACTION("Chain33.QueryTransaction"),
	
	BLOCKCHAIN_IS_SYNC("Chain33.IsSync"),
	
	SEND_TRANSACTION("Chain33.SendTransaction"),
	
	GET_TX_BY_HASHES("Chain33.GetTxByHashes"),
	
	GET_HEX_TX_BY_HASH("Chain33.GetHexTxByHash"),
	
	GET_BLOCKS("Chain33.GetBlocks"),
	
	GET_LAST_HEADER("Chain33.GetLastHeader"),
	
	GET_HEADERS("Chain33.GetHeaders"),
	
	GET_BLOCK_HASH("Chain33.GetBlockHash"),
	
	GET_BLOCK_DETAIL("Chain33.GetBlockOverview"),
	
	GET_PEER_INFO("Chain33.GetPeerInfo"),
	
	GET_WALLET_STUATUS("Chain33.GetWalletStatus"),
	
	LOCK_WALLET("Chain33.Lock"),
	
	UNLOCK_WALLET("Chain33.UnLock"),
	
	NEW_ACCOUNT("Chain33.NewAccount"),
	
	GEN_SEED("Chain33.GenSeed"),
	
	SAVE_SEED("Chain33.SaveSeed"),
	
	GET_SEED("Chain33.GetSeed"),
	
	SET_LABEL("Chain33.SetLabl"),
	
	GET_ACCOUNT_LIST("Chain33.GetAccounts"),
	
	TOKEN_CREATE_PRE_CREATE_TX("token.CreateRawTokenPreCreateTx"),
	
	TOKEN_CREATE_FINISH_TX("token.CreateRawTokenFinishTx"),
	
	TOKEN_CREATE_RAW_TX("Chain33.CreateRawTransaction"),
	
	SIGN_RAW_TRANSACTION("Chain33.SignRawTx"),
	
	GET_TOKEN_BALANCE("token.GetTokenBalance"),
	
	QUERY("Chain33.Query"),
	
	GET_TX_BY_ADDR("Chain33.GetTxByAddr"),
	
	SEND_RAW_TRANSACTION("Chain33.SendRawTransaction"),
	
	SEND_TO_ADDRESS("Chain33.SendToAddress"),
	
	GET_BALANCE("Chain33.GetBalance"),
	
	CREATE_TRASACTION("Chain33.CreateTransaction"),
	
	DUMP_PRIVKEY("Chain33.DumpPrivkey"),
	
	IMPORT_PRIVKEY("Chain33.ImportPrivkey"),
		
	CREATE_NO_BALANCE_TX("Chain33.CreateNoBalanceTransaction"),
	
	CONVERT_EXECER_TO_ADDRESS("Chain33.ConvertExectoAddr");

	private String method;
	
	private RpcMethod(String method) {
		this.method = method;
	}
	
	public String getMethod() {
		return method;
	}
	
	@Override
	public String toString() {
		return getMethod();
	}
		
}
