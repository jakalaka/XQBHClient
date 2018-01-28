package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.Client.Table.Mapper.DSPXXMapper;
import XQBHClient.Client.Table.Model.DSPXX;
import XQBHClient.Client.Table.Model.DSPXXKey;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.Utils.log.Logger;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class GetSPNum {
    public static int exec(String position ) {
        int iNum = 0;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
        } catch (IOException e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SESSION);
            return 0;
        }
        DSPXXMapper dspxxMapper = sqlSession.getMapper(DSPXXMapper.class);
        DSPXXKey dspxxKey = new DSPXXKey();
        dspxxKey.setFRDM_U("9999");
        dspxxKey.setSPLJ_U(position);
        DSPXX dspxx = null;
        try {
            dspxx = dspxxMapper.selectByPrimaryKey(dspxxKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SELECT);
            return 0;
        }
        return dspxx.getSL_UUU();
    }
}
