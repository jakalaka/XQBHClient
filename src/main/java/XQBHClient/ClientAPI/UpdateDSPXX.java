package XQBHClient.ClientAPI;

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
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "获取数据库会话失败!");
        }

        DSPXXMapper dspxxMapper=sqlSession.getMapper(DSPXXMapper.class);
        DSPXXKey dspxxKey=new DSPXXKey();
        dspxxKey.setFRDM_U("9999");
        dspxxKey.setSPMC_U(thingsName);
        DSPXX dspxx;
        dspxx = dspxxMapper.selectByPrimaryKey(dspxxKey);
        if (null==dspxx)
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新商品库存时查询数据库无记录!");
            return false;
        }
        dspxx.setSL_UUU(dspxx.getSL_UUU()+iNum);
        dspxxMapper.updateByPrimaryKey(dspxx);

        sqlSession.commit();
        sqlSession.close();


        return true;
    }
}
