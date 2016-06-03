package se.manage.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.manage.user.User;
import se.manage.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    @Autowired
    StockRepository stockRepository;

    public StockService() {
    }

    @Transactional
    public boolean adjust(String account, String code, String delta) {
        List<UserStock> records= this.stockRepository.findByAccount(account);
        if (!records.isEmpty()) {
            int n = records.size();
            for (int i = 0 ; i< n; i++) {
                UserStock r = records.get(i);
                if (r.getCode().equals(code)) {
                    if(r.increase(delta))
                        return true;
                    else return false;
                }
            }
        }
        UserStock temp=new UserStock(account, code, delta);
        this.stockRepository.save(temp);
        return true;
    }

    @Transactional
    public String check(String account, String code) {
        List<UserStock> records= this.stockRepository.findByAccount(account);
        if (!records.isEmpty()) {
            int n = records.size();
            for (int i = 0 ; i< n; i++) {
                UserStock r = records.get(i);
                if (r.getCode().equals(code)) {
                    return r.getAmount();
                }
            }
        }
        return 0+"";
    }

    @Transactional
    public boolean noStock(String account) {
        List<UserStock> records= this.stockRepository.findByAccount(account);
        if (!records.isEmpty()) {
            int n = records.size();
            for (int i = 0 ; i< n; i++) {
                UserStock r = records.get(i);
                if (!r.getAmount().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }


}
