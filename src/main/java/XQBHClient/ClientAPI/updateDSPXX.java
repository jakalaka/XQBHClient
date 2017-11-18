package XQBHClient.ClientAPI;

import XQBHClient.Client.Table.Mapper.DSPXXMapper;
import XQBHClient.Client.Table.Model.DSPXX;
import XQBHClient.Client.Table.Model.DSPXXKey;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.Utils.log.Logger;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class updateDSPXX {
    public static boolean  exec(String thingsName,int iNum) {
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("LOG_ERR",e.toString());
        }

        DSPXXMapper dspxxMapper=sqlSession.getMapper(DSPXXMapper.class);
        DSPXXKey dspxxKey=new DSPXXKey();
        dspxxKey.setFRDM_U("9999");
        dspxxKey.setSPMC_U(thingsName);
        DSPXX dspxx=dspxxMapper.selectByPrimaryKey(dspxxKey);
        if (null==dspxx)
        {
            Logger.log("LOG_ERR","更新商品库存时查询数据库无记录");
            return false;
        }
        dspxx.setSL_UUU(dspxx.getSL_UUU()+iNum);
        dspxxMapper.updateByPrimaryKey(dspxx);

        sqlSession.commit();
        sqlSession.close();


        return true;
    }
}
