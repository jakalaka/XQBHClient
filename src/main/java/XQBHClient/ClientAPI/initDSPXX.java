package XQBHClient.ClientAPI;

import XQBHClient.Client.Table.Mapper.DSPXXMapper;
import XQBHClient.Client.Table.Model.DSPXX;
import XQBHClient.Client.Table.Model.DSPXXExample;
import XQBHClient.Client.Table.Model.DSPXXKey;
import XQBHClient.Client.Table.basic.DBAccess;
import XQBHClient.Utils.log.Logger;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class initDSPXX {
    public static boolean  createData(String thingsName){
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
            dspxx=new DSPXX();
            dspxx.setFRDM_U("9999");
            dspxx.setJLZT_U("0");
            dspxx.setSL_UUU(0);
            dspxx.setSPMC_U(thingsName);
            dspxxMapper.insert(dspxx);
        }else {
            if (!"0".equals(dspxx.getJLZT_U()))
            {
                dspxx.setJLZT_U("0");
                dspxxMapper.updateByPrimaryKey(dspxx);
            }else
            {
                //do nothing
            }
        }
        sqlSession.commit();
        sqlSession.close();
        return true;

    }
    public static boolean  initTable(){
        DBAccess dbAccess=new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("LOG_ERR",e.toString());
        }

        DSPXXExample example = new DSPXXExample();
        DSPXXExample.Criteria criteria = example.createCriteria();
        criteria.andFRDM_UEqualTo("9999");
        DSPXX dspxx = new DSPXX();
        dspxx.setJLZT_U("1");
        DSPXXMapper dspxxMapper=sqlSession.getMapper(DSPXXMapper.class);
        dspxxMapper.updateByExampleSelective(dspxx,example);


        sqlSession.commit();
        sqlSession.close();
        return true;

    }
}
