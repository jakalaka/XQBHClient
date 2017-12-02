package XQBHClient.ClientAPI;

import XQBHClient.Client.Com;
import XQBHClient.Client.Table.Mapper.DSPXXMapper;
import XQBHClient.Client.Table.Model.DSPXX;
import XQBHClient.Client.Table.Model.DSPXXKey;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.Utils.log.Logger;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class UpdateDSPXX {
    public static boolean  exec(String thingsName,int iNum) {
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
        } catch (IOException e) {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SESSION);
            return false;
        }

        DSPXXMapper dspxxMapper=sqlSession.getMapper(DSPXXMapper.class);
        DSPXXKey dspxxKey=new DSPXXKey();
        dspxxKey.setFRDM_U("9999");
        dspxxKey.setSPMC_U(thingsName);
        DSPXX dspxx;
        try {
            dspxx = dspxxMapper.selectByPrimaryKey(dspxxKey);
        }catch (Exception e)
        {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SELECT);
            return false;
        }
        if (null==dspxx)
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新商品库存时查询数据库无记录!");
            return false;
        }
        dspxx.setSL_UUU(dspxx.getSL_UUU()+iNum);
        try {
            dspxxMapper.updateByPrimaryKey(dspxx);

        }catch (Exception e)
        {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_UPDATE);
            return false;
        }

        sqlSession.commit();
        sqlSession.close();


        return true;
    }
}
